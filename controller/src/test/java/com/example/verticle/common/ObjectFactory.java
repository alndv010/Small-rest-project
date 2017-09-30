package com.example.verticle.common;

import com.example.model.CreateAccountRequest;
import com.example.model.CreateTransferRequest;

import java.util.UUID;
import java.util.function.Function;

/**
 * Created by remote on 7/24/17.
 */
public class ObjectFactory {
    static public Function<Double, CreateAccountRequest> newCreateAccountRequest = balance -> {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccountId(UUID.randomUUID().toString());
        createAccountRequest.setDescription("");
        createAccountRequest.setInitialBalance(balance);
        return createAccountRequest;
    };

    static public CreateTransferRequest newCreateTransferRequest(double balance, String from, String to) {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest();
        createTransferRequest.setTransferId(UUID.randomUUID().toString());
        createTransferRequest.setFromAccountId(from);
        createTransferRequest.setToAccountId(to);
        createTransferRequest.setAmount(balance);
        return createTransferRequest;
    }
}
