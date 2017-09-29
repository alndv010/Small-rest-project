package io.swagger.server.api.verticle.transaction;

import io.swagger.server.api.verticle.RequestControllerUtils;
import io.swagger.server.common.ApiException;
import io.swagger.server.common.Wrapper;
import model.CreateTransactionRequest;
import model.CreateTransactionResponse;

import java.util.concurrent.CompletableFuture;

import static io.swagger.server.util.ApiUtil.getResponse;
import static io.swagger.server.util.ApiUtil.processSingleResponse;

/**
 * Created by remote on 9/29/17.
 */
public class TransactionControllerUtils extends RequestControllerUtils {
    public Wrapper<CreateTransactionResponse> createTransactionRequest(CreateTransactionRequest accountResponse) throws ApiException {
        final CompletableFuture<Wrapper<CreateTransactionResponse>> callFuture = new CompletableFuture<>();
        getWebClient().post(port, host, prefix + "/transactions")
                .sendJson(accountResponse, ar -> processSingleResponse(callFuture, ar, CreateTransactionResponse.class));
        return getResponse(callFuture);
    }
}
