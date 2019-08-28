package io.bineng.api.client.exception;

import io.bineng.api.client.BiNengApiError;

/**
 * An exception which can occur while invoking methods of the BHex API.
 */
public class BiNengApiException extends RuntimeException {

    private static final long serialVersionUID = 3788669840036201041L;
    /**
     * Error response object returned by BHex API.
     */
    private BiNengApiError error;

    /**
     * Instantiates a new BHex api exception.
     *
     * @param error an error response object
     */
    public BiNengApiException(BiNengApiError error) {
        this.error = error;
    }

    /**
     * Instantiates a new BHex api exception.
     */
    public BiNengApiException() {
        super();
    }

    /**
     * Instantiates a new BHex api exception.
     *
     * @param message the message
     */
    public BiNengApiException(String message) {
        super(message);
    }

    /**
     * Instantiates a new BHex api exception.
     *
     * @param cause the cause
     */
    public BiNengApiException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new BHex api exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BiNengApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @return the response error object from BHex API, or null if no response object was returned (e.g. server returned 500).
     */
    public BiNengApiError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        if (error != null) {
            return error.getMsg();
        }
        return super.getMessage();
    }
}
