package io.swagger.server.api.verticle;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import io.swagger.server.api.MainModule;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;


/**
 * Created by remote on 7/31/17.
 */
public abstract class RequestControllerUtils {
    protected Vertx vertx = Vertx.vertx();
    protected String prefix = "/v1";

    @Inject
    @Named("port")
    protected int port;

    @Inject
    @Named("server_host")
    protected String host;


    public RequestControllerUtils() {
        Injector injector = Guice.createInjector(new MainModule());
        injector.injectMembers(this);
    }

    protected WebClient getWebClient() {
        return WebClient.create(vertx);
    }
}
