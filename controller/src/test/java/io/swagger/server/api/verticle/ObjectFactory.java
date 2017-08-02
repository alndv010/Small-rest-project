package io.swagger.server.api.verticle;

import model.CreateAccountRequest;
import model.CreateTransactionRequest;

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

    static public CreateTransactionRequest newCreateTransactionRequest(double balance, String from, String to) {
        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
        createTransactionRequest.setTransactionId(UUID.randomUUID().toString());
        createTransactionRequest.setFromAccountId(from);
        createTransactionRequest.setToAccountId(to);
        createTransactionRequest.setAmount(balance);
        return createTransactionRequest;
    }
}
