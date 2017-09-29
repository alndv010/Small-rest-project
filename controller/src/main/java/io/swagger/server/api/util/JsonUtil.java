package io.swagger.server.api.util;

import com.github.phiz71.vertx.swagger.router.SwaggerRouter;
import io.swagger.server.api.MainApiException;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by remote on 8/3/17.
 */
@Log4j2
public class JsonUtil {

    static public void processAsyncResult(Message<JsonObject> message, AsyncResult<?> result, String serviceName, int responseCode) {
        if (result.succeeded()) {
            DeliveryOptions deliveryOptions = new DeliveryOptions();
            deliveryOptions.addHeader(SwaggerRouter.CUSTOM_STATUS_CODE_HEADER_KEY, String.valueOf(responseCode));
            message.reply(new JsonObject(Json.encode(result.result())).encodePrettily(), deliveryOptions);
        } else {
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
        final int code;
        final String statusMessage;
        if (cause instanceof MainApiException) {
            code = ((MainApiException)cause).getStatusCode();
            statusMessage = ((MainApiException)cause).getStatusMessage();
        } else {
            code = MainApiException.INTERNAL_SERVER_ERROR.getStatusCode();
            statusMessage = MainApiException.INTERNAL_SERVER_ERROR.getStatusMessage();
            log.error("Unexpected error in "+ serviceName, cause);
        }
        message.fail(code, statusMessage);
    }

    public static <T> Optional<T> getJsonParameterFromMessageBody(Message<JsonObject> message, String name, Class<T> tClass, String serviceName)  {
        try {
            return Optional.of(Json.mapper.readValue(message.body().getJsonObject(name).encode(), tClass));
        } catch (IOException e) {
            manageError(message, e, serviceName);
            return Optional.empty();
        }
    }
}
