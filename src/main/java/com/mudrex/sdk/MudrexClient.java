package com.mudrex.sdk;

import com.google.gson.Gson;
import com.mudrex.sdk.exceptions.MudrexException;
import com.mudrex.sdk.models.OtherModels;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Main Mudrex API client
 */
public class MudrexClient implements AutoCloseable {
    private final String apiSecret;
    private final String baseURL;
    private final Duration timeout;
    private final HttpClient httpClient;
    private final Gson gson;
    private final RateLimiter rateLimiter;

    public final WalletAPI wallet;
    public final AssetsAPI assets;
    public final LeverageAPI leverage;
    public final OrdersAPI orders;
    public final PositionsAPI positions;
    public final FeesAPI fees;

    /**
     * Creates a new Mudrex API client
     */
    public MudrexClient(String apiSecret) {
        this(apiSecret, "https://trade.mudrex.com/fapi/v1", Duration.ofSeconds(30));
    }

    /**
     * Creates a new Mudrex API client with custom configuration
     */
    public MudrexClient(String apiSecret, String baseURL, Duration timeout) {
        this.apiSecret = apiSecret;
        this.baseURL = baseURL;
        this.timeout = timeout;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(timeout)
                .build();
        this.gson = new Gson();
        this.rateLimiter = new RateLimiter(2.0); // 2 requests per second

        // Initialize API modules
        this.wallet = new WalletAPI(this);
        this.assets = new AssetsAPI(this);
        this.leverage = new LeverageAPI(this);
        this.orders = new OrdersAPI(this);
        this.positions = new PositionsAPI(this);
        this.fees = new FeesAPI(this);
    }

    /**
     * Performs a GET request
     */
    public byte[] get(String path) throws MudrexException {
        return doRequest("GET", path, null);
    }

    /**
     * Performs a POST request
     */
    public byte[] post(String path, String body) throws MudrexException {
        return doRequest("POST", path, body);
    }

    /**
     * Performs a PATCH request
     */
    public byte[] patch(String path, String body) throws MudrexException {
        return doRequest("PATCH", path, body);
    }

    /**
     * Performs a DELETE request
     */
    public byte[] delete(String path, String body) throws MudrexException {
        return doRequest("DELETE", path, body);
    }

    /**
     * Internal method to perform HTTP requests
     */
    private byte[] doRequest(String method, String path, String body) throws MudrexException {
        // Apply rate limiting
        rateLimiter.wait();

        String url = baseURL + path;
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(timeout)
                .header("X-Authentication", apiSecret)
                .header("Content-Type", "application/json");

        if ("GET".equals(method)) {
            builder.GET();
        } else if ("POST".equals(method)) {
            builder.POST(HttpRequest.BodyPublishers.ofString(body != null ? body : ""));
        } else if ("PATCH".equals(method)) {
            builder.method("PATCH", HttpRequest.BodyPublishers.ofString(body != null ? body : ""));
        } else if ("DELETE".equals(method)) {
            builder.DELETE();
        }

        try {
            HttpRequest request = builder.build();
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            
            // Check for errors
            checkForErrors(response.statusCode(), response.body());
            
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new MudrexException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Checks HTTP response for errors and throws appropriate exceptions
     */
    private void checkForErrors(int statusCode, byte[] body) throws MudrexException {
        if (statusCode < 400) {
            return;
        }

        String bodyStr = new String(body, StandardCharsets.UTF_8);
        try {
            OtherModels.APIResponse<?> response = gson.fromJson(bodyStr, OtherModels.APIResponse.class);
            String message = response.message != null ? response.message : bodyStr;
            int code = response.error != null ? response.error.code : -1;

            if (statusCode == 401) {
                throw new MudrexException("Authentication failed: " + message, code, statusCode);
            } else if (statusCode == 429) {
                throw new MudrexException("Rate limit exceeded: " + message, code, statusCode);
            } else if (statusCode == 400) {
                if (code == 1002 || message.contains("insufficient balance")) {
                    throw new MudrexException("Insufficient balance: " + message, code, statusCode);
                }
                throw new MudrexException("Validation error: " + message, code, statusCode);
            } else if (statusCode == 404) {
                throw new MudrexException("Not found: " + message, code, statusCode);
            } else if (statusCode == 409) {
                throw new MudrexException("Conflict: " + message, code, statusCode);
            } else if (statusCode >= 500) {
                throw new MudrexException("Server error: " + message, code, statusCode);
            } else {
                throw new MudrexException("API error: " + message, code, statusCode);
            }
        } catch (com.google.gson.JsonSyntaxException e) {
            throw new MudrexException("Failed to parse error response: " + bodyStr, -1, statusCode);
        }
    }

    /**
     * Gets the Gson instance for JSON serialization
     */
    public Gson getGson() {
        return gson;
    }

    @Override
    public void close() {
        // No resources to clean up for HttpClient
    }
}

/**
 * Simple rate limiter implementation
 */
class RateLimiter {
    private final double minInterval;
    private long lastRequestTime = 0;
    private final ReentrantLock lock = new ReentrantLock();

    RateLimiter(double requestsPerSecond) {
        this.minInterval = 1000.0 / requestsPerSecond; // Convert to milliseconds
    }

    void wait() {
        lock.lock();
        try {
            long now = System.currentTimeMillis();
            long elapsed = now - lastRequestTime;
            
            if (elapsed < minInterval) {
                long sleepTime = (long) (minInterval - elapsed);
                Thread.sleep(sleepTime);
            }
            
            lastRequestTime = System.currentTimeMillis();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
