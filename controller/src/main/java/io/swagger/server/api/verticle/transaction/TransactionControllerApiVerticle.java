package io.swagger.server.api.verticle.transaction;

import com.google.inject.Inject;
import io.swagger.server.api.util.JsonUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import model.CreateTransactionRequest;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import static io.swagger.server.api.util.JsonUtil.getJsonParameterFromMessageBody;

@Log4j2
public class TransactionControllerApiVerticle extends AbstractVerticle {
    final static String CREATE_TRANSACTION = "createTransaction";

    @Inject
    private TransactionControllerApi service;

    @Override
    public void start() throws Exception {

        vertx.eventBus().<JsonObject> consumer(CREATE_TRANSACTION).handler(message -> {
                Optional<CreateTransactionRequest> request = getJsonParameterFromMessageBody(message, "request", CreateTransactionRequest.class, CREATE_TRANSACTION);
                if (request.isPresent())
                    service.transactionsPost(request.get(),
                        result -> JsonUtil.processAsyncResult(message, result, CREATE_TRANSACTION, 201));
        });
        
    }


}
