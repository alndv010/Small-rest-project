package io.swagger.server.api.verticle;

import io.swagger.server.api.MainApiException;
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
    RequestControllerUtils requestUtils = new RequestControllerUtils();

    @Test(timeout = 2000)
    public void create_new_account(TestContext context) throws MainApiException {
        CreateAccountRequest createAccountRequest = ObjectFactory.newCreateAccountRequest.apply(3d);
        CreateAccountResponse createAccountResponse = requestUtils.createAccountResponse(createAccountRequest);
        GetAccountResponse getAccountResponse = requestUtils.getAccountResponse(createAccountResponse.getAccountId());
        context.assertEquals(getAccountResponse.getAccountId(), createAccountRequest.getAccountId());
        context.assertEquals(getAccountResponse.getBalance(), createAccountRequest.getInitialBalance());
    }

}
