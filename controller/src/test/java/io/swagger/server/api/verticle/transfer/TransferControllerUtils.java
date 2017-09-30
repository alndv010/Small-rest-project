package io.swagger.server.api.verticle.transfer;

import io.swagger.server.api.verticle.RequestControllerUtils;
import io.swagger.server.common.ApiException;
import io.swagger.server.common.Wrapper;
import model.CreateTransferRequest;
import model.CreateTransferResponse;

import java.util.concurrent.CompletableFuture;

import static io.swagger.server.util.ApiUtil.getResponse;
import static io.swagger.server.util.ApiUtil.processSingleResponse;

/**
 * Created by remote on 9/29/17.
 */
public class TransferControllerUtils extends RequestControllerUtils {
    public Wrapper<CreateTransferResponse> createTransferRequest(CreateTransferRequest accountResponse) throws ApiException {
        final CompletableFuture<Wrapper<CreateTransferResponse>> callFuture = new CompletableFuture<>();
        getWebClient().post(port, host, prefix + "/transfers")
                .sendJson(accountResponse, ar -> processSingleResponse(callFuture, ar, CreateTransferResponse.class));
        return getResponse(callFuture);
    }
}
