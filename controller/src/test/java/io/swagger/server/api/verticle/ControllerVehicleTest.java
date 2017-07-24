package io.swagger.server.api.verticle;


import io.swagger.server.api.MainApiVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.web.client.WebClient;
import model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.swagger.server.api.MainApiVerticle.PORT;

/**
 * Created by remote on 7/22/17.
 */
public abstract class ControllerVehicleTest {

    protected static Vertx vertx;
    protected static HttpClient httpClient;
    final protected static String HOST = "localhost";

    @BeforeClass
    public static void beforeClass(TestContext context) {
        Async before = context.async();

        vertx = Vertx.vertx();
        vertx.deployVerticle(MainApiVerticle.class.getCanonicalName(), res -> {
            if (res.succeeded()) {
                before.complete();
            } else {
                context.fail(res.cause());
            }
        });

        httpClient = vertx.createHttpClient();

    }

    @AfterClass
    public static void afterClass(TestContext context) {
        Async after = context.async();
        FileSystem vertxFileSystem = vertx.fileSystem();
        vertxFileSystem.deleteRecursive(".vertx", true, vertxDir -> {
            if (vertxDir.succeeded()) {
                after.complete();
            } else {
                context.fail(vertxDir.cause());
            }
        });
    }

    public GetAccountResponse getAccountResponse(String accountId) {
        Future<GetAccountResponse> callFuture = Future.future();
        httpClient.getNow(PORT, HOST, "/v1/accounts/" + accountId, response -> {
            response.bodyHandler(body -> {
                GetAccountResponse getAccountResponse = null;
                try {
                    JsonObject jsonObject = new JsonObject(body.toString());
                    getAccountResponse = Json.mapper.readValue(jsonObject.encode(), GetAccountResponse.class);
                } catch (Exception e) {
                }
                callFuture.complete(getAccountResponse);
            });
        });
        waitCompletedFuture(callFuture);
        return callFuture.result();
    }

    public CreateAccountResponse createAccountResponse(CreateAccountRequest createAccountRequest) {
        Future<CreateAccountResponse> callFuture = Future.future();
        WebClient client = WebClient.create(vertx);
        client.post(PORT, HOST, "/v1/accounts")
                .sendJson(createAccountRequest, ar -> {
                    CreateAccountResponse createAccountResponse = null;
                    if (ar.succeeded()) {
                        createAccountResponse = ar.result().bodyAsJson(CreateAccountResponse.class);
                    }
                    callFuture.complete(createAccountResponse);
                });
        waitCompletedFuture(callFuture);
        return callFuture.result();
    }


    public List<AccountTransactionInfo> getTransactionInfoList(String accountId) {
        Future<List<AccountTransactionInfo>> callFuture = Future.future();
        httpClient.getNow(PORT, HOST, "/v1/accounts/" + accountId +"/transactions", response -> {
            response.bodyHandler(body -> {
                List<AccountTransactionInfo> transactionInfoList = new ArrayList<>();
                JsonArray jsonArray = new JsonArray(body.toString());
                try {
                    for(Object jsonObject : jsonArray) {
                        if (jsonObject instanceof JsonObject) {
                            AccountTransactionInfo accountTransactionInfo = Json.mapper.readValue(((JsonObject) jsonObject).encode(), AccountTransactionInfo.class);
                            transactionInfoList.add(accountTransactionInfo);
                        }
                    }
                } catch (Exception e) {
                }
                callFuture.complete(transactionInfoList);
            });
            response.exceptionHandler(err -> {
                callFuture.complete(Collections.emptyList());
            });
        });
        waitCompletedFuture(callFuture);
        return callFuture.result();
    }


    public CreateAccountResponse createAccountResponse(CreateAccountResponse accountResponse) {
        Future<CreateAccountResponse> callFuture = Future.future();
        WebClient client = WebClient.create(vertx);
        client.post(PORT, HOST, "/v1/accounts")
                .sendJson(accountResponse, ar -> {
                    CreateAccountResponse createAccountResponse = null;
                    if (ar.succeeded()) {
                        createAccountResponse = ar.result().bodyAsJson(CreateAccountResponse.class);
                    }
                    callFuture.complete(createAccountResponse);
                });
        return callFuture.result();
    }

    public CreateTransactionResponse createTransactionRequest(CreateTransactionRequest accountResponse) {
        Future<CreateTransactionResponse> callFuture = Future.future();
        WebClient client = WebClient.create(vertx);
        client.post(PORT, HOST, "/v1/transactions")
                .sendJson(accountResponse, ar -> {
                    CreateTransactionResponse createTransactionResponse = null;
                    if (ar.succeeded()) {
                        createTransactionResponse = ar.result().bodyAsJson(CreateTransactionResponse.class);
                    }
                    callFuture.complete(createTransactionResponse);
                });
        waitCompletedFuture(callFuture);
        return callFuture.result();
    }

    protected void waitCompletedFuture(Future callFuture) {
        while (!callFuture.isComplete()){
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
        }
    }



}
