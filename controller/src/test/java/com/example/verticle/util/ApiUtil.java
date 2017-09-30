package com.example.verticle.util;

import com.example.verticle.common.ApiException;
import com.example.verticle.common.Wrapper;
import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by remote on 9/29/17.
 */
public class ApiUtil {

    public static <K> void processListResponse(CompletableFuture<Wrapper<List<K>>> callFuture, AsyncResult<HttpResponse<Buffer>> ar, Class<K> kClass) {
        processResponse(callFuture, ar, getWrappedListResponse(ar, kClass));
    }

    public static <K> void processSingleResponse(CompletableFuture<Wrapper<K>> callFuture, AsyncResult<HttpResponse<Buffer>> ar, Class<K> kClass) {
        processResponse(callFuture, ar, getWrappedResponse(ar, kClass));
    }

    public static <T> T getResponse(CompletableFuture<T> callFuture) throws ApiException {
        try {
            return callFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            if (e.getCause() instanceof ApiException) {
                throw (ApiException) e.getCause();
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    private static <T> void processResponse(CompletableFuture<T> callFuture, AsyncResult<HttpResponse<Buffer>> ar, T result) {
        if (ar.succeeded()) {
            if (ar.result().statusCode() >= 200 && ar.result().statusCode() < 300) {
                callFuture.complete(result);
            } else {
                callFuture.completeExceptionally(new ApiException(ar.result().statusCode(), ar.result().statusMessage()));
            }
        } else {
            callFuture.completeExceptionally(ar.cause());
        }
    }

    private static <K> Wrapper<List<K>> getWrappedListResponse(AsyncResult<HttpResponse<Buffer>> response, Class<K> kClass) {
        try {
            final List<K> objects = new ArrayList<>();
            JsonArray jsonArray = response.result().bodyAsJsonArray();
            for (Object jsonObject : jsonArray) {
                K processedObject = Json.mapper.readValue(((JsonObject) jsonObject).encode(), kClass);
                objects.add(processedObject);
            }
            return new Wrapper<>(objects, response.result().statusCode());
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <K> Wrapper<K> getWrappedResponse(AsyncResult<HttpResponse<Buffer>> response, Class<K> kClass) {
        K t = response.result().bodyAsJson(kClass);
        return new Wrapper<>(t, response.result().statusCode());
    }
}
