package io.swagger.server.api.verticle.account;

import com.example.exception.AccountNotFound;
import com.example.AccountService;
import com.example.TransferInfoService;
import com.example.model.AccountData;
import com.example.model.TransferInfoData;
import com.google.inject.Inject;
import model.AccountTransferInfo;
import model.CreateAccountRequest;
import model.CreateAccountResponse;
import model.GetAccountResponse;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by remote on 7/17/17.
 */
@Log4j2
public class AccountControllerApiImpl implements AccountControllerApi {
    @Inject
    private AccountService accountService;

    @Inject
    private TransferInfoService transferInfoService;

    @Override
    public void accountsAccountIdGet(String accountId, Handler<AsyncResult<GetAccountResponse>> handler) {
        try {
            AccountData accountData = accountService.getAccount(accountId);
            GetAccountResponse getAccountResponse =
                    new GetAccountResponse(accountData.getAccountId(), accountData.getBalance(), accountData.getDescription());
            handler.handle(Future.succeededFuture(getAccountResponse));
        } catch (AccountNotFound e){
            handler.handle(Future.failedFuture(e.getClass().getSimpleName()));
        }
    }

    @Override
    public void accountsAccountIdTransfersGet(String accountId, Handler<AsyncResult<List<AccountTransferInfo>>> handler) {
        List<TransferInfoData> transferInfoDataList = transferInfoService.getAccountTransferInfos(accountId);
        List<AccountTransferInfo> transferInfos = new ArrayList<>();
        for (TransferInfoData transferInfoData : transferInfoDataList) {
            AccountTransferInfo transferInfo = new AccountTransferInfo();
            transferInfo.setTransferId(transferInfoData.getTransferInfoId());
            transferInfo.setFromAccountId(transferInfoData.getFromAccountId());
            transferInfo.setToAccountId(transferInfoData.getToAccountId());
            transferInfo.setAmount(transferInfoData.getAmount().longValue());
            transferInfos.add(transferInfo);
        }
        handler.handle(Future.succeededFuture(transferInfos));
    }

    @Override
    public void accountsPost(CreateAccountRequest request, Handler<AsyncResult<CreateAccountResponse>> handler) {
        AccountData accountData = new AccountData(request.getAccountId(), request.getInitialBalance(), request.getDescription());
        accountService.createAccount(accountData);
        handler.handle(Future.succeededFuture(new CreateAccountResponse(accountData.getAccountId())));
    }
}
