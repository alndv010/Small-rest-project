package io.swagger.server.api;

import com.adamlewis.guice.persist.jooq.JooqPersistModule;
import com.example.AccountService;
import com.example.TransactionInfoService;
import com.example.dao.AccountDAO;
import com.example.dao.TransactionInfoDAO;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.db.ManagedDataSource;
import com.yammer.dropwizard.db.ManagedDataSourceFactory;
import io.swagger.server.api.verticle.*;
import lombok.extern.log4j.Log4j2;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by remote on 7/22/17.
 */

@Log4j2
public class MainModule extends AbstractModule {

    @Inject
    @Named("db.username")
    private String user;

    @Inject
    @Named("db.url")
    private String url;

    @Override
    protected void configure() {
        install(new JooqPersistModule());
        bind(DataSource.class).to(ManagedDataSource.class);
        bind(TransactionInfoService.class);
        bind(AccountService.class);
        bind(AccountDAO.class);
        bind(TransactionInfoDAO.class);
        bind(TransactionControllerApi.class).to(TransactionControllerApiImpl.class);
        bind(AccountControllerApi.class).to(AccountControllerApiImpl.class);
        loadPropertyFile();
    }

    private void loadPropertyFile() {
        String resourceName = "project.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try(InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
            props.load(resourceStream);
        } catch (IOException e) {
            log.error("can't load property file", e);
        }
        Names.bindProperties(binder(), props);
    }

    @Provides
    private DatabaseConfiguration getDatabaseConfiguration() {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setDriverClass(org.h2.Driver.class.getCanonicalName());
        databaseConfiguration.setUser(user);
        databaseConfiguration.setUrl(url);
        return databaseConfiguration;
    }


    @Provides
    ManagedDataSource dataSource(final ManagedDataSourceFactory factory, final DatabaseConfiguration configuration) throws ClassNotFoundException {
        return factory.build(configuration);
    }

    @Provides
    public SQLDialect dialect() {
        return SQLDialect.H2;
    }

    @Provides
    public Configuration configuration(DataSource dataSource, SQLDialect dialect) {
        return new DefaultConfiguration()
                .set(dataSource)
                .set(dialect);
    }
}
