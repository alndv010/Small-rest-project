package io.swagger.server.api.verticle.account;

import model.AccountTransferInfo;
import model.CreateAccountRequest;
import model.CreateAccountResponse;
import model.GetAccountResponse;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

public interface AccountControllerApi  {

    void accountsAccountIdGet(String accountId, Handler<AsyncResult<GetAccountResponse>> handler);

    void accountsAccountIdTransfersGet(String accountId, Handler<AsyncResult<List<AccountTransferInfo>>> handler);

    void accountsPost(CreateAccountRequest request, Handler<AsyncResult<CreateAccountResponse>> handler);
    
}
