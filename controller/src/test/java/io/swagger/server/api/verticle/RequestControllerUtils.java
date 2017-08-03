package io.swagger.server.api.verticle;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import io.swagger.server.api.MainApiException;
import io.swagger.server.api.MainModule;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * Created by remote on 7/31/17.
 */
public class RequestControllerUtils {
    private Vertx vertx = Vertx.vertx();
    private String prefix = "/v1";

    @Inject
    @Named("port")
    private int port;

    @Inject
    @Named("server_host")
    private String host;


    public RequestControllerUtils() {
        Injector injector = Guice.createInjector(new MainModule());
        injector.injectMembers(this);
    }

    public GetAccountResponse getAccountResponse(String accountId) throws MainApiException {
        CompletableFuture<GetAccountResponse> callFuture = new CompletableFuture<>();
        WebClient.create(vertx).get(port, host, prefix + "/accounts/" + accountId).send(ar -> {
            final GetAccountResponse getAccountResponse;
            if (ar.succeeded()) {
                try {
                    JsonObject jsonObject = ar.result().bodyAsJsonObject();
                    getAccountResponse = Json.mapper.readValue(jsonObject.encode(), GetAccountResponse.class);
                    callFuture.complete(getAccountResponse);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                callFuture.completeExceptionally(ar.cause());
            }
        });
        return getResponse(callFuture);
    }

    public CreateAccountResponse createAccountResponse(CreateAccountRequest createAccountRequest) throws MainApiException {
        CompletableFuture<CreateAccountResponse> callFuture = new CompletableFuture<>();
        WebClient.create(vertx).post(port, host, prefix + "/accounts")
                .sendJson(createAccountRequest, ar -> {
                    final CreateAccountResponse createAccountResponse;
                    if (ar.succeeded()) {
                        createAccountResponse = ar.result().bodyAsJson(CreateAccountResponse.class);
                        callFuture.complete(createAccountResponse);
                    } else {
                        callFuture.completeExceptionally(ar.cause());
                    }
                });
        return getResponse(callFuture);
    }


    public List<AccountTransactionInfo> getTransactionInfoList(String accountId) throws MainApiException {
        CompletableFuture<List<AccountTransactionInfo>> callFuture = new CompletableFuture<>();
        WebClient.create(vertx).get(port, host, prefix + "/accounts/" + accountId + "/transactions").send(response -> {
            List<AccountTransactionInfo> transactionInfoList = new ArrayList<>();
            if (response.succeeded()) {
                JsonArray jsonArray = response.result().bodyAsJsonArray();
                try {
                    for (Object jsonObject : jsonArray) {
                        AccountTransactionInfo accountTransactionInfo = Json.mapper.readValue(((JsonObject) jsonObject).encode(), AccountTransactionInfo.class);
                        transactionInfoList.add(accountTransactionInfo);
                    }
                    callFuture.complete(transactionInfoList);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                callFuture.completeExceptionally(response.cause());
            }
        });
        return getResponse(callFuture);
    }


    public CreateAccountResponse createAccountResponse(CreateAccountResponse accountResponse) throws MainApiException {
        CompletableFuture<CreateAccountResponse> callFuture = new CompletableFuture<>();
        WebClient.create(vertx).post(port, host, prefix + "/accounts")
                .sendJson(accountResponse, ar -> {
                    final CreateAccountResponse createAccountResponse;
                    if (ar.succeeded()) {
                        createAccountResponse = ar.result().bodyAsJson(CreateAccountResponse.class);
                        callFuture.complete(createAccountResponse);
                    } else {
                        callFuture.completeExceptionally(ar.cause());
                    }
                });
        return getResponse(callFuture);
    }

    public CreateTransactionResponse createTransactionRequest(CreateTransactionRequest accountResponse) throws MainApiException {
        CompletableFuture<CreateTransactionResponse> callFuture = new CompletableFuture<>();
        WebClient.create(vertx).post(port, host, prefix + "/transactions")
                .sendJson(accountResponse, ar -> {
                    final CreateTransactionResponse createTransactionResponse;
                    if (ar.succeeded()) {
                        if (ar.result().statusCode() == 200) {
                            createTransactionResponse = ar.result().bodyAsJson(CreateTransactionResponse.class);
                            callFuture.complete(createTransactionResponse);
                        } else {
                            callFuture.completeExceptionally(new MainApiException(ar.result().statusCode(), ar.result().statusMessage()));
                        }
                    } else {
                        callFuture.completeExceptionally(ar.cause());
                    }
                });
        return getResponse(callFuture);
    }

    private <T> T getResponse(CompletableFuture<T> callFuture) throws MainApiException {
        try {
            return callFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof MainApiException) {
                throw (MainApiException) e.getCause();
            } else {
                throw new RuntimeException(e);
            }
        }
    }
}
