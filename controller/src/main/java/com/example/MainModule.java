package com.example;

import com.example.repository.AccountRepository;
import com.example.repository.TransferInfoRepository;
import com.example.verticle.transfer.TransferControllerApiImpl;
import com.google.inject.*;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.hubspot.guice.transactional.TransactionalDataSource;
import com.hubspot.guice.transactional.TransactionalModule;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.example.verticle.account.AccountControllerApi;
import com.example.verticle.account.AccountControllerApiImpl;
import com.example.verticle.transfer.TransferControllerApi;
import lombok.extern.log4j.Log4j2;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by remote on 7/22/17.
 */

@Log4j2
public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        loadPropertyFile();
        install(new TransactionalModule());
        bind(DataSource.class).toProvider(DataSourceProvider.class).in(Scopes.SINGLETON);
        bind(TransferInfoService.class);
        bind(AccountService.class);
        bind(AccountRepository.class);
        bind(TransferInfoRepository.class);
        bind(TransferControllerApi.class).to(TransferControllerApiImpl.class);
        bind(AccountControllerApi.class).to(AccountControllerApiImpl.class);
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

    static public class DataSourceProvider implements Provider<DataSource> {
        final private String user;
        final private String url;

        @Inject
        public DataSourceProvider(@Named("db.username") String user,
                                  @Named("db.url") String url) {
            this.user = user;
            this.url = url;
        }

        @Override
        public DataSource get() {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(org.h2.Driver.class.getCanonicalName());
            config.setJdbcUrl(url);
            config.setUsername(user);
            return new TransactionalDataSource(new HikariDataSource(config));
        }
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

    @Provides
    public DSLContext dslContext(Configuration configuration) {
        return new DefaultDSLContext(configuration);
    }
}
