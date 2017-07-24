package io.swagger.server.api.verticle;

import com.example.AccountNotFound;
import com.example.AccountService;
import com.example.TransactionInfoService;
import com.example.model.AccountData;
import com.example.model.TransactionInfoData;
import com.google.inject.Inject;
import model.AccountTransactionInfo;
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
    private TransactionInfoService transactionInfoService;

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
    public void accountsAccountIdTransactionsGet(String accountId, Handler<AsyncResult<List<AccountTransactionInfo>>> handler) {
        List<TransactionInfoData> transactionInfoDataList = transactionInfoService.getAccountTransactionInfos(accountId);
        List<AccountTransactionInfo> transactionInfos = new ArrayList<>();
        for (TransactionInfoData transactionInfoData : transactionInfoDataList) {
            AccountTransactionInfo transactionInfo = new AccountTransactionInfo();
            transactionInfo.setTransactionId(transactionInfoData.getTransactionInfoId());
            transactionInfo.setFromAccountId(transactionInfoData.getFromAccountId());
            transactionInfo.setToAccountId(transactionInfoData.getToAccountId());
            transactionInfo.setAmount(transactionInfoData.getAmount().longValue());
            transactionInfos.add(transactionInfo);
        }
        handler.handle(Future.succeededFuture(transactionInfos));
    }

    @Override
    public void accountsPost(CreateAccountRequest request, Handler<AsyncResult<CreateAccountResponse>> handler) {
        AccountData accountData = new AccountData(request.getAccountId(), request.getInitialBalance(), request.getDescription());
        accountService.createAccount(accountData);
        handler.handle(Future.succeededFuture(new CreateAccountResponse(accountData.getAccountId())));
    }
}
