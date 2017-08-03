package io.swagger.server.api.verticle;

import io.swagger.server.api.MainApiException;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

/**
 * Created by remote on 8/3/17.
 */
@Log4j2
public class JsonUtil {

    static public void processAsyncResult(Message<JsonObject> message, AsyncResult<?> result, String serviceName) {
        if (result.succeeded())
            message.reply(new JsonObject(Json.encode(result.result())).encodePrettily());
        else {
            Throwable cause = result.cause();
            manageError(message, cause, serviceName);
        }
    }

    static public void processAsyncArrayResult(Message<JsonObject> message, AsyncResult<?> result, String serviceName) {
        if (result.succeeded())
            message.reply(new JsonArray(Json.encode(result.result())).encodePrettily());
        else {
            Throwable cause = result.cause();
            manageError(message, cause, serviceName);
        }
    }

    static public void manageError(Message<JsonObject> message, Throwable cause, String serviceName) {
        int code = MainApiException.INTERNAL_SERVER_ERROR.getStatusCode();
        String statusMessage = MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage();
        if (cause instanceof MainApiException) {
            code = ((MainApiException)cause).getStatusCode();
            statusMessage = ((MainApiException)cause).getStatusMessage();
        } else {
            log.error("Unexpected error in "+ serviceName, cause);
        }
        message.fail(code, statusMessage);
    }
}
