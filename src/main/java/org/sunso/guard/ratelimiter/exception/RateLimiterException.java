package org.sunso.guard.ratelimiter.exception;

public class RateLimiterException extends RuntimeException {

    public RateLimiterException(String message) {
        super(message);
    }
    public RateLimiterException(String message, Throwable t) {
        super(message, t);
    }
}
