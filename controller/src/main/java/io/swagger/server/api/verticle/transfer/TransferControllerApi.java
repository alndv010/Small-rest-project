package io.swagger.server.api.verticle.transfer;

import model.CreateTransferRequest;
import model.CreateTransferResponse;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface TransferControllerApi  {

    void transfersPost(CreateTransferRequest request, Handler<AsyncResult<CreateTransferResponse>> handler);
    
}
