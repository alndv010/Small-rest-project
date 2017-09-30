package com.example.verticle.account;

import com.example.verticle.RequestControllerUtils;
import com.example.verticle.common.ApiException;
import com.example.verticle.common.Wrapper;
import com.example.model.AccountTransferInfo;
import com.example.model.CreateAccountRequest;
import com.example.model.CreateAccountResponse;
import com.example.model.GetAccountResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.example.verticle.util.ApiUtil.getResponse;
import static com.example.verticle.util.ApiUtil.processListResponse;
import static com.example.verticle.util.ApiUtil.processSingleResponse;

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

    public Wrapper<List<AccountTransferInfo>> getTransferInfoList(String accountId) throws ApiException {
        CompletableFuture<Wrapper<List<AccountTransferInfo>>> callFuture = new CompletableFuture<>();
        getWebClient().get(port, host, prefix + "/accounts/" + accountId + "/transfers")
                .send(ar -> processListResponse(callFuture, ar, AccountTransferInfo.class));
        return getResponse(callFuture);
    }

}
