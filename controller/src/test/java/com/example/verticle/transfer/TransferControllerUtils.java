package com.example.verticle.transfer;

import com.example.verticle.RequestControllerUtils;
import com.example.verticle.common.ApiException;
import com.example.verticle.common.Wrapper;
import com.example.model.CreateTransferRequest;
import com.example.model.CreateTransferResponse;

import java.util.concurrent.CompletableFuture;

import static com.example.verticle.util.ApiUtil.getResponse;
import static com.example.verticle.util.ApiUtil.processSingleResponse;

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
