package io.swagger.server.api.verticle.account;

import com.google.inject.Inject;
import io.swagger.server.api.util.JsonUtil;
import io.vertx.core.AbstractVerticle;

import io.vertx.core.json.JsonObject;

import model.CreateAccountRequest;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import static io.swagger.server.api.util.JsonUtil.getJsonParameterFromMessageBody;


@Log4j2
public class AccountControllerApiVerticle extends AbstractVerticle {
    final static String GET_ACCOUNT = "getAccount";
    final static String GET_TRANSFERS_FOR_ACCOUNT = "getTransfersForAccount";
    final static String CREATE_ACCOUNT = "createAccount";
    final static String ACCOUNT_ID = "accountId";

    @Inject
    private AccountControllerApi service;

    @Override
    public void start() throws Exception {
        vertx.eventBus().<JsonObject> consumer(GET_ACCOUNT).handler(message -> {
                String accountId = message.body().getString(ACCOUNT_ID);
                service.accountsAccountIdGet(accountId,
                        result -> JsonUtil.processAsyncResult(message, result, GET_ACCOUNT, 200));
        });

        vertx.eventBus().<JsonObject> consumer(GET_TRANSFERS_FOR_ACCOUNT).handler(message -> {
                String accountId = message.body().getString(ACCOUNT_ID);
                service.accountsAccountIdTransfersGet(accountId,
                        result -> JsonUtil.processAsyncArrayResult(message, result, GET_TRANSFERS_FOR_ACCOUNT));
        });

        vertx.eventBus().<JsonObject> consumer(CREATE_ACCOUNT).handler(message -> {
                Optional<CreateAccountRequest> request = getJsonParameterFromMessageBody(message, "request", CreateAccountRequest.class, CREATE_ACCOUNT);
                if (request.isPresent()) {
                    service.accountsPost(request.get(),
                            result -> JsonUtil.processAsyncResult(message, result, CREATE_ACCOUNT, 201));
                }
        });
        
    }





}
