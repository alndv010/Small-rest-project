package io.swagger.server.api.verticle.account;

import io.swagger.server.api.verticle.ControllerVehicleTest;
import io.swagger.server.common.ApiException;
import io.swagger.server.common.ObjectFactory;
import io.swagger.server.common.Wrapper;
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
    private AccountControllerUtils requestUtils = new AccountControllerUtils();

    @Test(timeout = 2000)
    public void create_new_account(TestContext context) throws ApiException {
        final CreateAccountRequest createAccountRequest = ObjectFactory.newCreateAccountRequest.apply(3d);

        final Wrapper<CreateAccountResponse> createAccountResponse = requestUtils.createAccountResponse(createAccountRequest);
        final Wrapper<GetAccountResponse> accountResponse = requestUtils.getAccountResponse(createAccountResponse.getResponse().getAccountId());
        context.assertEquals(201, createAccountResponse.getCode());
        context.assertEquals(200, accountResponse.getCode());

        context.assertEquals(accountResponse.getResponse().getAccountId(), createAccountRequest.getAccountId());
        context.assertEquals(accountResponse.getResponse().getBalance(), createAccountRequest.getInitialBalance());
    }

}
