package com.example.exception;

import io.swagger.server.api.MainApiException;
import lombok.Getter;

/**
 * Created by remote on 7/23/17.
 */
public class AccountNotFound extends MainApiException {
    @Getter
    private String accountId;

    public AccountNotFound(String accountId) {
        super(400, "AccountNotFound");
        this.accountId = accountId;
    }
}
