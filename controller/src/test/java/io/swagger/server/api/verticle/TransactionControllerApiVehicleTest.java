package io.swagger.server.api.verticle;

import com.example.NotEnoughMoneyToTransfer;
import io.swagger.server.api.MainApiException;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.unit.Async;
import io.vertx.ext.web.client.WebClient;
import model.*;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.swagger.server.api.MainApiVerticle.PORT;

/**
 * Created by remote on 7/22/17.
 */

@RunWith(VertxUnitRunner.class)
public class TransactionControllerApiVehicleTest extends ControllerVehicleTest {

    @Test(timeout = 3000)
    public void create_new_money_transfer(TestContext context) {
        //init new accounts
        CreateAccountRequest createFromAccountRequest = ObjectFactory.getCreateAccountRequest(3);
        String from = createAccountResponse(createFromAccountRequest).getAccountId();
        CreateAccountRequest createToAccountRequest = ObjectFactory.getCreateAccountRequest(5);
        String to = createAccountResponse(createToAccountRequest).getAccountId();
        //create money transfer transaction
        CreateTransactionRequest createTransactionRequest = ObjectFactory.getCreateTransactionRequest(2, from, to);
        CreateTransactionResponse createTransactionResponse = createTransactionRequest(createTransactionRequest);
        //check results
        GetAccountResponse fromAccount = getAccountResponse(from);
        GetAccountResponse toAccount = getAccountResponse(to);

        context.assertEquals(fromAccount.getBalance().intValue(), 1);
        context.assertEquals(toAccount.getBalance().intValue(), 7);

        context.assertEquals(createTransactionRequest.getTransactionId(), getTransactionInfoList(from).get(0).getTransactionId());
        context.assertEquals(createTransactionRequest.getTransactionId(), getTransactionInfoList(to).get(0).getTransactionId());
    }


    @Test(timeout = 2000)
    public void should_not_create_new_money_transfer(TestContext context) {
            //init new accounts
            CreateAccountRequest createFromAccountRequest = ObjectFactory.getCreateAccountRequest(1);
            String from = createAccountResponse(createFromAccountRequest).getAccountId();
            CreateAccountRequest createToAccountRequest = ObjectFactory.getCreateAccountRequest(3);
            String to = createAccountResponse(createToAccountRequest).getAccountId();
            //create money transfer transaction
            CreateTransactionRequest createTransactionRequest = ObjectFactory.getCreateTransactionRequest(2, from, to);
            CreateTransactionResponse createTransactionResponse = createTransactionRequest(createTransactionRequest);
            //check results
            GetAccountResponse fromAccount = getAccountResponse(from);
            GetAccountResponse toAccount = getAccountResponse(to);
            context.assertEquals(toAccount.getBalance().intValue(), 3);
            context.assertEquals(fromAccount.getBalance().intValue(), 1);
            context.assertEquals(createTransactionResponse, null);
    }
}
