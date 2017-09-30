package com.example.verticle;


import com.example.MainApiVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Created by remote on 7/22/17.
 */
public abstract class ControllerVehicleTest {
    protected static Vertx vertx;

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




}
