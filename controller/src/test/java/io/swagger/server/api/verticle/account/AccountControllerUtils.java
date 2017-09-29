package io.swagger.server.api.verticle.account;

import io.swagger.server.api.verticle.RequestControllerUtils;
import io.swagger.server.common.ApiException;
import io.swagger.server.common.Wrapper;
import model.AccountTransactionInfo;
import model.CreateAccountRequest;
import model.CreateAccountResponse;
import model.GetAccountResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.swagger.server.util.ApiUtil.getResponse;
import static io.swagger.server.util.ApiUtil.processListResponse;
import static io.swagger.server.util.ApiUtil.processSingleResponse;

/**
 * Created by remote on 9/29/17.
 */
public class AccountControllerUtils extends RequestControllerUtils {

    public Wrapper<GetAccountResponse> getAccountResponse(String accountId) throws ApiException {
        CompletableFuture<Wrapper<GetAccountResponse>> callFuture = new CompletableFuture<>();
        getWebClient().get(port, host, prefix + "/accounts/" + accountId)
                .send(ar -> processSingleResponse(callFuture, ar, GetAccountResponse.class));
        return getResponse(callFuture);
    }

    public Wrapper<CreateAccountResponse> createAccountResponse(CreateAccountRequest createAccountRequest) throws ApiException {
        CompletableFuture<Wrapper<CreateAccountResponse>> callFuture = new CompletableFuture<>();
        getWebClient().post(port, host, prefix + "/accounts")
                .sendJson(createAccountRequest, ar -> processSingleResponse(callFuture, ar, CreateAccountResponse.class));
        return getResponse(callFuture);
    }

    public Wrapper<List<AccountTransactionInfo>> getTransactionInfoList(String accountId) throws ApiException {
        CompletableFuture<Wrapper<List<AccountTransactionInfo>>> callFuture = new CompletableFuture<>();
        getWebClient().get(port, host, prefix + "/accounts/" + accountId + "/transactions")
                .send(ar -> processListResponse(callFuture, ar, AccountTransactionInfo.class));
        return getResponse(callFuture);
    }

}
