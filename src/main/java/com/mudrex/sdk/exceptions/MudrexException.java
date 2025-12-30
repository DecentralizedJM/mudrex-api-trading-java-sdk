package com.mudrex.sdk.exceptions;

/**
 * Base exception for Mudrex API errors
 */
public class MudrexException extends Exception {
    private final int code;
    private final int httpStatus;

    public MudrexException(String message, int code, int httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public MudrexException(String message, Throwable cause) {
        super(message, cause);
        this.code = -1;
        this.httpStatus = -1;
    }

    public int getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}

/**
 * Authentication error (401)
 */
class AuthenticationException extends MudrexException {
    public AuthenticationException(String message, int code) {
        super(message, code, 401);
    }
}

/**
 * Rate limit error (429)
 */
class RateLimitException extends MudrexException {
    public RateLimitException(String message, int code) {
        super(message, code, 429);
    }
}

/**
 * Validation error (400)
 */
class ValidationException extends MudrexException {
    public ValidationException(String message, int code) {
        super(message, code, 400);
    }
}

/**
 * Not found error (404)
 */
class NotFoundException extends MudrexException {
    public NotFoundException(String message, int code) {
        super(message, code, 404);
    }
}

/**
 * Conflict error (409)
 */
class ConflictException extends MudrexException {
    public ConflictException(String message, int code) {
        super(message, code, 409);
    }
}

/**
 * Server error (5xx)
 */
class ServerException extends MudrexException {
    public ServerException(String message, int code, int httpStatus) {
        super(message, code, httpStatus);
    }
}

/**
 * Insufficient balance error (400 with specific code)
 */
class InsufficientBalanceException extends MudrexException {
    public InsufficientBalanceException(String message, int code) {
        super(message, code, 400);
    }
}
