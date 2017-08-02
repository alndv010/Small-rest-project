package io.swagger.server.api.verticle;

import io.swagger.server.api.MainApiException;
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
    RequestControllerUtils requestUtils = new RequestControllerUtils();

    @Test(timeout = 2000)
    public void create_new_money_transfer(TestContext context) throws MainApiException {
        //init new accounts
        CreateAccountRequest createFromAccountRequest = ObjectFactory.newCreateAccountRequest.apply(3d);
        String from = requestUtils.createAccountResponse(createFromAccountRequest).getAccountId();
        CreateAccountRequest createToAccountRequest = ObjectFactory.newCreateAccountRequest.apply(5d);
        String to = requestUtils.createAccountResponse(createToAccountRequest).getAccountId();
        //create money transfer transaction
        CreateTransactionRequest createTransactionRequest = ObjectFactory.newCreateTransactionRequest(2, from, to);
        CreateTransactionResponse createTransactionResponse = requestUtils.createTransactionRequest(createTransactionRequest);
        //check results
        GetAccountResponse fromAccount = requestUtils.getAccountResponse(from);
        GetAccountResponse toAccount = requestUtils.getAccountResponse(to);

        context.assertEquals(fromAccount.getBalance().intValue(), 1);
        context.assertEquals(toAccount.getBalance().intValue(), 7);

        context.assertEquals(createTransactionRequest.getTransactionId(), requestUtils.getTransactionInfoList(from).get(0).getTransactionId());
        context.assertEquals(createTransactionRequest.getTransactionId(), requestUtils.getTransactionInfoList(to).get(0).getTransactionId());
    }


    @Test(timeout = 2000, expected = MainApiException.class)
    public void should_not_create_new_money_transfer(TestContext context) throws MainApiException {
        //init new accounts
        CreateAccountRequest createFromAccountRequest = ObjectFactory.newCreateAccountRequest.apply(1d);
        String from = requestUtils.createAccountResponse(createFromAccountRequest).getAccountId();
        CreateAccountRequest createToAccountRequest = ObjectFactory.newCreateAccountRequest.apply(3d);
        String to = requestUtils.createAccountResponse(createToAccountRequest).getAccountId();
        //create money transfer transaction
        CreateTransactionRequest createTransactionRequest = ObjectFactory.newCreateTransactionRequest(2, from, to);
        CreateTransactionResponse createTransactionResponse = requestUtils.createTransactionRequest(createTransactionRequest);
        //check results
        GetAccountResponse fromAccount = requestUtils.getAccountResponse(from);
        GetAccountResponse toAccount = requestUtils.getAccountResponse(to);

        context.assertEquals(toAccount.getBalance().intValue(), 3);
        context.assertEquals(fromAccount.getBalance().intValue(), 1);
    }
}
