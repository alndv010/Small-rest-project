package io.swagger.server.api.verticle;

import io.vertx.ext.unit.TestContext;
import model.CreateAccountRequest;
import model.CreateAccountResponse;
import model.GetAccountResponse;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by remote on 7/22/17.
 */
@RunWith(VertxUnitRunner.class)
public class AccountControllerApiVehicleTest extends ControllerVehicleTest {


    @Test(timeout = 2000)
    public void create_new_account(TestContext context) {
        CreateAccountRequest createAccountRequest = ObjectFactory.getCreateAccountRequest(3);
        CreateAccountResponse createAccountResponse = createAccountResponse(createAccountRequest);
        GetAccountResponse getAccountResponse = getAccountResponse(createAccountResponse.getAccountId());
        context.assertEquals(getAccountResponse.getAccountId(), createAccountRequest.getAccountId());
        context.assertEquals(getAccountResponse.getBalance(), createAccountRequest.getInitialBalance());
    }

}
