package io.swagger.server.api.verticle;

import io.swagger.server.api.MainApiException;

public final class AccountControllerApiException extends MainApiException {

    public AccountControllerApiException(int statusCode, String statusMessage) {
        super(statusCode, statusMessage);
    }

}