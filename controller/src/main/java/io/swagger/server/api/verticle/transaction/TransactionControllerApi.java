package io.swagger.server.api.verticle.transaction;

import model.CreateTransactionRequest;
import model.CreateTransactionResponse;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface TransactionControllerApi  {

    void transactionsPost(CreateTransactionRequest request, Handler<AsyncResult<CreateTransactionResponse>> handler);
    
}
