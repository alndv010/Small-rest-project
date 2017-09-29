package io.swagger.server.common;

public class ApiException extends Exception {
    private int statusCode;
    private String statusMessage;

    public ApiException(int statusCode, String statusMessage) {
        super();
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}