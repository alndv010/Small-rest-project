package com.example.verticle.transfer;

import com.example.model.CreateTransferRequest;
import com.example.model.CreateTransferResponse;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface TransferControllerApi  {

    void transfersPost(CreateTransferRequest request, Handler<AsyncResult<CreateTransferResponse>> handler);
    
}
