package io.swagger.server.api;

import com.adamlewis.guice.persist.jooq.JooqPersistModule;
import com.example.AccountService;
import com.example.TransactionInfoService;
import com.example.dao.AccountDAO;
import com.example.dao.TransactionInfoDAO;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.db.ManagedDataSource;
import com.yammer.dropwizard.db.ManagedDataSourceFactory;
import io.swagger.server.api.verticle.*;
import org.jooq.Configuration;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;

import javax.sql.DataSource;

/**
 * Created by remote on 7/22/17.
 */

public class MainModule extends AbstractModule {

    private final DatabaseConfiguration configuration;

    public MainModule(final DatabaseConfiguration configuration) {
        this.configuration = configuration;
    }

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
    }


    @Provides
    ManagedDataSource dataSource(final ManagedDataSourceFactory factory) throws ClassNotFoundException {
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
