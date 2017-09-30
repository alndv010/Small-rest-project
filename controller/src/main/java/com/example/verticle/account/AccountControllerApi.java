package com.example.verticle.account;

import com.example.model.AccountTransferInfo;
import com.example.model.CreateAccountRequest;
import com.example.model.CreateAccountResponse;
import com.example.model.GetAccountResponse;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

public interface AccountControllerApi  {

    void accountsAccountIdGet(String accountId, Handler<AsyncResult<GetAccountResponse>> handler);

    void accountsAccountIdTransfersGet(String accountId, Handler<AsyncResult<List<AccountTransferInfo>>> handler);

    void accountsPost(CreateAccountRequest request, Handler<AsyncResult<CreateAccountResponse>> handler);
    
}
