package io.swagger.server.api.verticle;

import com.google.inject.Inject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import model.CreateTransactionRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TransactionControllerApiVerticle extends AbstractVerticle {
    final static String CREATE_TRANSACTION = "createTransaction";

    @Inject
    private TransactionControllerApi service;

    @Override
    public void start() throws Exception {

        vertx.eventBus().<JsonObject> consumer(CREATE_TRANSACTION).handler(message -> {
            try {
                CreateTransactionRequest request = Json.mapper.readValue(message.body().getJsonObject("request").encode(), CreateTransactionRequest.class);
                service.transactionsPost(request, result -> {
                    JsonUtil.processAsyncResult(message, result, CREATE_TRANSACTION);
                });
            } catch (Exception e) {
                JsonUtil.manageError(message, e, CREATE_TRANSACTION);
            }
        });
        
    }
}
