package io.swagger.server.api.verticle.transfer;

import com.google.inject.Inject;
import io.swagger.server.api.util.JsonUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import model.CreateTransferRequest;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

import static io.swagger.server.api.util.JsonUtil.getJsonParameterFromMessageBody;

@Log4j2
public class TransferControllerApiVerticle extends AbstractVerticle {
    final static String CREATE_TRANSFER = "createTransfer";

    @Inject
    private TransferControllerApi service;

    @Override
    public void start() throws Exception {

        vertx.eventBus().<JsonObject> consumer(CREATE_TRANSFER).handler(message -> {
                Optional<CreateTransferRequest> request = getJsonParameterFromMessageBody(message, "request", CreateTransferRequest.class, CREATE_TRANSFER);
                if (request.isPresent())
                    service.transfersPost(request.get(),
                        result -> JsonUtil.processAsyncResult(message, result, CREATE_TRANSFER, 201));
        });
        
    }


}
