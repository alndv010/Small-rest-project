package io.swagger.server.api.verticle;

import com.google.inject.Inject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import model.CreateAccountRequest;
import io.swagger.server.api.MainApiException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AccountControllerApiVerticle extends AbstractVerticle {
    final static String GET_ACCOUNTS_ACCOUNTID_SERVICE_ID = "getAccount";
    final static String GET_ACCOUNTS_ACCOUNTID_TRANSACTIONS_SERVICE_ID = "getTransactionsForAccount";
    final static String POST_ACCOUNTS_SERVICE_ID = "createAccount";

    @Inject
    private AccountControllerApi service;

    @Override
    public void start() throws Exception {
        vertx.eventBus().<JsonObject> consumer(GET_ACCOUNTS_ACCOUNTID_SERVICE_ID).handler(message -> {
            try {
                String accountId = message.body().getString("accountId");
                service.accountsAccountIdGet(accountId, result -> {
                    if (result.succeeded())
                        message.reply(new JsonObject(Json.encode(result.result())).encodePrettily());
                    else {
                        Throwable cause = result.cause();
                        manageError(message, cause, GET_ACCOUNTS_ACCOUNTID_SERVICE_ID);
                    }
                });
            } catch (Exception e) {
                manageError(message, e, GET_ACCOUNTS_ACCOUNTID_SERVICE_ID);
            }
        });
        vertx.eventBus().<JsonObject> consumer(GET_ACCOUNTS_ACCOUNTID_TRANSACTIONS_SERVICE_ID).handler(message -> {
            try {
                String accountId = message.body().getString("accountId");
                service.accountsAccountIdTransactionsGet(accountId, result -> {
                    if (result.succeeded())
                        message.reply(new JsonArray(Json.encode(result.result())).encodePrettily());
                    else {
                        Throwable cause = result.cause();
                        manageError(message, cause, GET_ACCOUNTS_ACCOUNTID_TRANSACTIONS_SERVICE_ID);
                    }
                });
            } catch (Exception e) {
                manageError(message, e, GET_ACCOUNTS_ACCOUNTID_TRANSACTIONS_SERVICE_ID);
            }
        });
        vertx.eventBus().<JsonObject> consumer(POST_ACCOUNTS_SERVICE_ID).handler(message -> {
            try {
                CreateAccountRequest request = Json.mapper.readValue(message.body().getJsonObject("request").encode(), CreateAccountRequest.class);
                service.accountsPost(request, result -> {
                    if (result.succeeded())
                        message.reply(new JsonObject(Json.encode(result.result())).encodePrettily());
                    else {
                        Throwable cause = result.cause();
                        manageError(message, cause, POST_ACCOUNTS_SERVICE_ID);
                    }
                });
            } catch (Exception e) {
                manageError(message, e, POST_ACCOUNTS_SERVICE_ID);
            }
        });
        
    }
    
    private void manageError(Message<JsonObject> message, Throwable cause, String serviceName) {
        int code = MainApiException.INTERNAL_SERVER_ERROR.getStatusCode();
        String statusMessage = MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage();
        if (cause instanceof MainApiException) {
            code = ((MainApiException)cause).getStatusCode();
            statusMessage = ((MainApiException)cause).getStatusMessage();
        } else {
            logUnexpectedError(serviceName, cause); 
        }
        message.fail(code, statusMessage);
    }
    
    private void logUnexpectedError(String serviceName, Throwable cause) {
        log.error("Unexpected error in "+ serviceName, cause);
    }
}
