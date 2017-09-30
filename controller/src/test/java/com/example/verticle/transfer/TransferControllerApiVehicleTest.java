package com.example.verticle.transfer;

import com.example.verticle.ControllerVehicleTest;
import com.example.verticle.account.AccountControllerUtils;
import com.example.verticle.common.ApiException;
import com.example.verticle.common.ObjectFactory;
import com.example.verticle.common.Wrapper;
import com.example.model.*;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by remote on 7/22/17.
 */

@RunWith(VertxUnitRunner.class)
public class TransferControllerApiVehicleTest extends ControllerVehicleTest {
    private AccountControllerUtils accountRequestUtils = new AccountControllerUtils();
    private TransferControllerUtils transferUtils = new TransferControllerUtils();

    @Test(timeout = 2000)
    public void create_new_money_transfer(TestContext context) throws ApiException {
        //init new accounts
        final CreateAccountRequest createFromAccountRequest = ObjectFactory.newCreateAccountRequest.apply(3d);
        final String from = accountRequestUtils.createAccountResponse(createFromAccountRequest).getResponse().getAccountId();
        final CreateAccountRequest createToAccountRequest = ObjectFactory.newCreateAccountRequest.apply(5d);
        final String to = accountRequestUtils.createAccountResponse(createToAccountRequest).getResponse().getAccountId();
        //create money transfer transfer
        final CreateTransferRequest createTransferRequest = ObjectFactory.newCreateTransferRequest(2, from, to);
        final Wrapper<CreateTransferResponse> createTransferResponse = transferUtils.createTransferRequest(createTransferRequest);
        //check results
        final Wrapper<GetAccountResponse> fromAccount = accountRequestUtils.getAccountResponse(from);
        final Wrapper<GetAccountResponse> toAccount = accountRequestUtils.getAccountResponse(to);

        context.assertEquals(201, createTransferResponse.getCode());
        context.assertEquals(200, fromAccount.getCode());
        context.assertEquals(200, toAccount.getCode());

        context.assertEquals(1, fromAccount.getResponse().getBalance().intValue());
        context.assertEquals(7, toAccount.getResponse().getBalance().intValue());

        context.assertEquals(createTransferRequest.getTransferId(), accountRequestUtils.getTransferInfoList(from).getResponse().get(0).getTransferId());
        context.assertEquals(createTransferRequest.getTransferId(), accountRequestUtils.getTransferInfoList(to).getResponse().get(0).getTransferId());
    }


    @Test(timeout = 2000, expected = ApiException.class)
    public void should_not_create_new_money_transfer(TestContext context) throws ApiException {
        //init new accounts
        final CreateAccountRequest createFromAccountRequest = ObjectFactory.newCreateAccountRequest.apply(1d);
        final String from = accountRequestUtils.createAccountResponse(createFromAccountRequest).getResponse().getAccountId();
        final CreateAccountRequest createToAccountRequest = ObjectFactory.newCreateAccountRequest.apply(3d);
        final String to = accountRequestUtils.createAccountResponse(createToAccountRequest).getResponse().getAccountId();
        //create money transfer transfer
        final CreateTransferRequest createTransferRequest = ObjectFactory.newCreateTransferRequest(2, from, to);
        try {
            transferUtils.createTransferRequest(createTransferRequest);
        } catch (ApiException e) {
            context.assertEquals("NotEnoughMoneyToTransfer", e.getStatusMessage());
            throw e;
        }
    }
}
