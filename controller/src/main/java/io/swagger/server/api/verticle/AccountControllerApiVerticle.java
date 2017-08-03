package io.swagger.server.api.verticle;

import com.google.inject.Inject;
import io.vertx.core.AbstractVerticle;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import model.CreateAccountRequest;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class AccountControllerApiVerticle extends AbstractVerticle {
    final static String GET_ACCOUNT = "getAccount";
    final static String GET_TRANSACTIONS_FOR_ACCOUNT = "getTransactionsForAccount";
    final static String CREATE_ACCOUNT = "createAccount";
    final static String ACCOUNT_ID = "accountId";

    @Inject
    private AccountControllerApi service;

    @Override
    public void start() throws Exception {
        vertx.eventBus().<JsonObject> consumer(GET_ACCOUNT).handler(message -> {
            try {
                String accountId = message.body().getString(ACCOUNT_ID);
                service.accountsAccountIdGet(accountId, result -> {
                    JsonUtil.processAsyncResult(message, result, GET_ACCOUNT);
                });
            } catch (Exception e) {
                JsonUtil.manageError(message, e, GET_ACCOUNT);
            }
        });

        vertx.eventBus().<JsonObject> consumer(GET_TRANSACTIONS_FOR_ACCOUNT).handler(message -> {
            try {
                String accountId = message.body().getString(ACCOUNT_ID);
                service.accountsAccountIdTransactionsGet(accountId, result -> {
                    JsonUtil.processAsyncArrayResult(message, result, GET_TRANSACTIONS_FOR_ACCOUNT);
                });
            } catch (Exception e) {
                JsonUtil.manageError(message, e, GET_TRANSACTIONS_FOR_ACCOUNT);
            }
        });

        vertx.eventBus().<JsonObject> consumer(CREATE_ACCOUNT).handler(message -> {
            try {
                CreateAccountRequest request = Json.mapper.readValue(message.body().getJsonObject("request").encode(), CreateAccountRequest.class);
                service.accountsPost(request, result -> {
                    JsonUtil.processAsyncResult(message, result, CREATE_ACCOUNT);
                });
            } catch (Exception e) {
                JsonUtil.manageError(message, e, CREATE_ACCOUNT);
            }
        });
        
    }





}
