package io.swagger.server.api;

import java.nio.charset.Charset;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.phiz71.vertx.swagger.router.OperationIdServiceIdResolver;
import com.github.phiz71.vertx.swagger.router.SwaggerRouter;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.server.api.verticle.account.AccountControllerApiVerticle;
import io.swagger.server.api.verticle.transfer.TransferControllerApiVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainApiVerticle extends AbstractVerticle {

    @Inject
    @Named("port")
    private int port;

    public MainApiVerticle(){
        MainModule mainModule = new MainModule();
        Injector injector = Guice.createInjector(mainModule);
        injector.injectMembers(this);
    }

    @Inject
    private AccountControllerApiVerticle accountControllerApiVerticle;

    @Inject
    private TransferControllerApiVerticle transferControllerApiVerticle;
    
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Json.mapper.registerModule(new JavaTimeModule());
        FileSystem vertxFileSystem = vertx.fileSystem();
        vertxFileSystem.readFile("swagger.json", readFile -> {
            if (readFile.succeeded()) {
                Swagger swagger = new SwaggerParser().parse(readFile.result().toString(Charset.forName("utf-8")));
                Router swaggerRouter = SwaggerRouter.swaggerRouter(Router.router(vertx), swagger, vertx.eventBus(), new OperationIdServiceIdResolver());

                deployVerticles(startFuture);

                vertx.createHttpServer()
                        .requestHandler(swaggerRouter::accept)
                        .listen(port);
                startFuture.complete();
            } else {
                startFuture.fail(readFile.cause());
            }
        });
    }

    private void deployVerticles(Future<Void> startFuture) {
        vertx.deployVerticle(accountControllerApiVerticle,
                res -> processDeployStatus(startFuture, res, accountControllerApiVerticle.getClass()));
        
        vertx.deployVerticle(transferControllerApiVerticle,
                res -> processDeployStatus(startFuture, res, transferControllerApiVerticle.getClass()));
    }

    private void processDeployStatus(Future<Void> startFuture, AsyncResult<String> res, Class vertClass) {
        if (res.succeeded()) {
            log.info(vertClass.getSimpleName() + " : Deployed");
        } else {
            startFuture.fail(res.cause());
            log.error(vertClass.getSimpleName() + " : Deployment failed");
        }
    }
}