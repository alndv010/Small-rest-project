package io.swagger.server.api;

import java.nio.charset.Charset;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.phiz71.vertx.swagger.router.OperationIdServiceIdResolver;
import com.github.phiz71.vertx.swagger.router.SwaggerRouter;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.server.api.verticle.AccountControllerApiVerticle;
import io.swagger.server.api.verticle.TransactionControllerApiVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainApiVerticle extends AbstractVerticle {
    final Router router = Router.router(vertx);

    final static private String USER = "sa";
    final static private String URL = "jdbc:h2:~/example7";
    final static public int PORT = 8080;

    @Inject
    private AccountControllerApiVerticle accountControllerApiVerticle;

    @Inject
    private TransactionControllerApiVerticle transactionControllerApiVerticle;
    
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Injector injector = Guice.createInjector(new MainModule(getDatabaseConfiguration()));
        injector.injectMembers(this);
        startWithGuice(startFuture);
    }



    private void startWithGuice(Future<Void> startFuture) {
        Json.mapper.registerModule(new JavaTimeModule());
        FileSystem vertxFileSystem = vertx.fileSystem();
        vertxFileSystem.readFile("swagger.json", readFile -> {
            if (readFile.succeeded()) {
                Swagger swagger = new SwaggerParser().parse(readFile.result().toString(Charset.forName("utf-8")));
                Router swaggerRouter = SwaggerRouter.swaggerRouter(Router.router(vertx), swagger, vertx.eventBus(), new OperationIdServiceIdResolver());

                deployVerticles(startFuture);

                vertx.createHttpServer()
                    .requestHandler(swaggerRouter::accept)
                    .listen(PORT);
                startFuture.complete();
            } else {
            	startFuture.fail(readFile.cause());
            }
        });
    }

    public void deployVerticles(Future<Void> startFuture) {
        
        vertx.deployVerticle(accountControllerApiVerticle, res -> {
            if (res.succeeded()) {
                log.info("AccountControllerApiVerticle : Deployed");
            } else {
                startFuture.fail(res.cause());
                log.error("AccountControllerApiVerticle : Deployement failed");
            }
        });
        
        vertx.deployVerticle(transactionControllerApiVerticle, res -> {
            if (res.succeeded()) {
                log.info("TransactionControllerApiVerticle : Deployed");
            } else {
                startFuture.fail(res.cause());
                log.error("TransactionControllerApiVerticle : Deployement failed");
            }
        });
        
    }

    private DatabaseConfiguration getDatabaseConfiguration() {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setDriverClass(org.h2.Driver.class.getCanonicalName());
        databaseConfiguration.setUser(USER);
        databaseConfiguration.setUrl(URL);
        return databaseConfiguration;
    }
}