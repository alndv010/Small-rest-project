package io.swagger.server.api.verticle;

import com.example.*;
import com.example.model.TransactionInfoData;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.CreateTransactionRequest;
import model.CreateTransactionResponse;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;


/**
 * Created by remote on 7/17/17.
 */
@Log4j2
public class TransactionControllerApiImpl implements TransactionControllerApi {
    @Inject
    private TransactionInfoService  transactionInfoService;

    @Override
    public void transactionsPost(CreateTransactionRequest request, Handler<AsyncResult<CreateTransactionResponse>> handler) {
        TransactionInfoData transactionInfoData = TransactionInfoData.builder()
                .transactionInfoId(request.getTransactionId())
                .toAccountId(request.getToAccountId())
                .fromAccountId(request.getFromAccountId())
                .amount(request.getAmount())
                .build();
        try {
            transactionInfoService.createTransactionInfo(transactionInfoData);
            handler.handle(Future.succeededFuture(new CreateTransactionResponse(transactionInfoData.getTransactionInfoId())));
        } catch (NotEnoughMoneyToTransfer|AccountNotFound |NegativeAmountMoneyToTransfer|FromAndToAccountsCoincide transferError) {
            log.warn(transferError);
            handler.handle(Future.failedFuture(transferError));
        }
    }
}
