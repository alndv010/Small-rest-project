package io.swagger.server.api.verticle.transaction;

import io.swagger.server.api.verticle.ControllerVehicleTest;
import io.swagger.server.api.verticle.account.AccountControllerUtils;
import io.swagger.server.common.ApiException;
import io.swagger.server.common.ObjectFactory;
import io.swagger.server.common.Wrapper;
import model.*;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by remote on 7/22/17.
 */

@RunWith(VertxUnitRunner.class)
public class TransactionControllerApiVehicleTest extends ControllerVehicleTest {
    private AccountControllerUtils accountRequestUtils = new AccountControllerUtils();
    private TransactionControllerUtils transactionUtils = new TransactionControllerUtils();

    @Test(timeout = 2000)
    public void create_new_money_transfer(TestContext context) throws ApiException {
        //init new accounts
        final CreateAccountRequest createFromAccountRequest = ObjectFactory.newCreateAccountRequest.apply(3d);
        final String from = accountRequestUtils.createAccountResponse(createFromAccountRequest).getResponse().getAccountId();
        final CreateAccountRequest createToAccountRequest = ObjectFactory.newCreateAccountRequest.apply(5d);
        final String to = accountRequestUtils.createAccountResponse(createToAccountRequest).getResponse().getAccountId();
        //create money transfer transaction
        final CreateTransactionRequest createTransactionRequest = ObjectFactory.newCreateTransactionRequest(2, from, to);
        final Wrapper<CreateTransactionResponse> createTransactionResponse = transactionUtils.createTransactionRequest(createTransactionRequest);
        //check results
        final Wrapper<GetAccountResponse> fromAccount = accountRequestUtils.getAccountResponse(from);
        final Wrapper<GetAccountResponse> toAccount = accountRequestUtils.getAccountResponse(to);

        context.assertEquals(201, createTransactionResponse.getCode());
        context.assertEquals(200, fromAccount.getCode());
        context.assertEquals(200, toAccount.getCode());

        context.assertEquals(1, fromAccount.getResponse().getBalance().intValue());
        context.assertEquals(7, toAccount.getResponse().getBalance().intValue());

        context.assertEquals(createTransactionRequest.getTransactionId(), accountRequestUtils.getTransactionInfoList(from).getResponse().get(0).getTransactionId());
        context.assertEquals(createTransactionRequest.getTransactionId(), accountRequestUtils.getTransactionInfoList(to).getResponse().get(0).getTransactionId());
    }


    @Test(timeout = 2000, expected = ApiException.class)
    public void should_not_create_new_money_transfer(TestContext context) throws ApiException {
        //init new accounts
        final CreateAccountRequest createFromAccountRequest = ObjectFactory.newCreateAccountRequest.apply(1d);
        final String from = accountRequestUtils.createAccountResponse(createFromAccountRequest).getResponse().getAccountId();
        final CreateAccountRequest createToAccountRequest = ObjectFactory.newCreateAccountRequest.apply(3d);
        final String to = accountRequestUtils.createAccountResponse(createToAccountRequest).getResponse().getAccountId();
        //create money transfer transaction
        final CreateTransactionRequest createTransactionRequest = ObjectFactory.newCreateTransactionRequest(2, from, to);
        try {
            transactionUtils.createTransactionRequest(createTransactionRequest);
        } catch (ApiException e) {
            context.assertEquals("NotEnoughMoneyToTransfer", e.getStatusMessage());
            throw e;
        }
    }
}
