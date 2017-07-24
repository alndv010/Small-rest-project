package io.swagger.server.api.verticle;

import io.swagger.server.api.MainApiException;

public final class TransactionControllerApiException extends MainApiException {

    public TransactionControllerApiException(int statusCode, String statusMessage) {
        super(statusCode, statusMessage);
    }

}