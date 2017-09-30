package io.swagger.server.api.verticle.transfer;

import com.example.*;
import com.example.exception.AccountNotFound;
import com.example.exception.FromAndToAccountsCoincide;
import com.example.exception.NegativeAmountMoneyToTransfer;
import com.example.exception.NotEnoughMoneyToTransfer;
import com.example.model.TransferInfoData;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.CreateTransferRequest;
import model.CreateTransferResponse;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;


/**
 * Created by remote on 7/17/17.
 */
@Log4j2
public class TransferControllerApiImpl implements TransferControllerApi {
    @Inject
    private TransferInfoService  transferInfoService;

    @Override
    public void transfersPost(CreateTransferRequest request, Handler<AsyncResult<CreateTransferResponse>> handler) {
        TransferInfoData transferInfoData = TransferInfoData.builder()
                .transferInfoId(request.getTransferId())
                .toAccountId(request.getToAccountId())
                .fromAccountId(request.getFromAccountId())
                .amount(request.getAmount())
                .build();
        try {
            transferInfoService.createTransferInfo(transferInfoData);
            handler.handle(Future.succeededFuture(new CreateTransferResponse(transferInfoData.getTransferInfoId())));
        } catch (NotEnoughMoneyToTransfer |AccountNotFound |NegativeAmountMoneyToTransfer |FromAndToAccountsCoincide transferError) {
            log.warn(transferError);
            handler.handle(Future.failedFuture(transferError));
        }
    }
}
