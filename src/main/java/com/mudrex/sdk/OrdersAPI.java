package com.mudrex.sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mudrex.sdk.exceptions.MudrexException;
import com.mudrex.sdk.models.Enums;
import com.mudrex.sdk.models.OrderModels;
import com.mudrex.sdk.models.OtherModels;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Orders API endpoints
 */
public class OrdersAPI {
    private final MudrexClient client;
    private final Gson gson;

    OrdersAPI(MudrexClient client) {
        this.client = client;
        this.gson = client.getGson();
    }

    /**
     * Create a new order
     */
    public OrderModels.Order create(String assetId, OrderModels.OrderRequest request) throws MudrexException {
        String path = "/futures/" + assetId + "/order";
        byte[] resp = client.post(path, gson.toJson(request));
        Type type = new TypeToken<OtherModels.APIResponse<OrderModels.Order>>(){}.getType();
        OtherModels.APIResponse<OrderModels.Order> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Create a market order
     */
    public OrderModels.Order createMarketOrder(String assetId, Enums.OrderType side, String quantity, String leverage) throws MudrexException {
        OrderModels.OrderRequest request = new OrderModels.OrderRequest(
                leverage, quantity, side, Enums.TriggerType.MARKET);
        return create(assetId, request);
    }

    /**
     * Create a limit order
     */
    public OrderModels.Order createLimitOrder(String assetId, Enums.OrderType side, String quantity, String price, String leverage) throws MudrexException {
        OrderModels.OrderRequest request = new OrderModels.OrderRequest(
                leverage, quantity, side, Enums.TriggerType.LIMIT);
        request.price = price;
        return create(assetId, request);
    }

    /**
     * List all open orders for an asset
     */
    public List<OrderModels.Order> listOpen(String assetId) throws MudrexException {
        String path = "/futures/" + assetId + "/orders";
        byte[] resp = client.get(path);
        Type type = new TypeToken<OtherModels.APIResponse<List<OrderModels.Order>>>(){}.getType();
        OtherModels.APIResponse<List<OrderModels.Order>> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Get a specific order
     */
    public OrderModels.Order get(String assetId, String orderId) throws MudrexException {
        String path = "/futures/" + assetId + "/order/" + orderId;
        byte[] resp = client.get(path);
        Type type = new TypeToken<OtherModels.APIResponse<OrderModels.Order>>(){}.getType();
        OtherModels.APIResponse<OrderModels.Order> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Get order history with pagination
     */
    public List<OrderModels.Order> getHistory(String assetId, int page, int perPage) throws MudrexException {
        StringBuilder path = new StringBuilder("/futures/").append(assetId).append("/orders/history");
        
        boolean first = true;
        if (page > 0) {
            path.append("?page=").append(page);
            first = false;
        }
        if (perPage > 0) {
            path.append(first ? "?" : "&").append("per_page=").append(perPage);
        }
        
        byte[] resp = client.get(path.toString());
        Type type = new TypeToken<OtherModels.APIResponse<List<OrderModels.Order>>>(){}.getType();
        OtherModels.APIResponse<List<OrderModels.Order>> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }

    /**
     * Cancel an order
     */
    public boolean cancel(String assetId, String orderId) throws MudrexException {
        String path = "/futures/" + assetId + "/order/" + orderId;
        client.delete(path, null);
        return true;
    }

    /**
     * Amend an existing order
     */
    public OrderModels.Order amend(String assetId, String orderId, String price, String quantity) throws MudrexException {
        String path = "/futures/" + assetId + "/order/" + orderId;
        Map<String, String> body = new HashMap<>();
        body.put("price", price);
        body.put("quantity", quantity);
        
        byte[] resp = client.patch(path, gson.toJson(body));
        Type type = new TypeToken<OtherModels.APIResponse<OrderModels.Order>>(){}.getType();
        OtherModels.APIResponse<OrderModels.Order> apiResp = gson.fromJson(
                new String(resp, StandardCharsets.UTF_8), type);
        return apiResp.data;
    }
}
