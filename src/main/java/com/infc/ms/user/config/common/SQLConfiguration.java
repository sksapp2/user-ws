package com.infc.ms.user.config.common;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
public class SQLConfiguration {

    @Bean
    @Qualifier("mysql")
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "pool")
                .option(ConnectionFactoryOptions.PROTOCOL, "mysql") // driver identifier, PROTOCOL is delegated as DRIVER by the pool.
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 3306)
                .option(ConnectionFactoryOptions.DATABASE, "user_ms")
                .option(ConnectionFactoryOptions.USER, "root")
                .option(ConnectionFactoryOptions.PASSWORD, "root")
                .build());
        return connectionFactory;

    }


    @Bean
    public R2dbcEntityTemplate mysqlR2dbcEntityOperations(@Qualifier("mysql") ConnectionFactory connectionFactory) {
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);
        return new R2dbcEntityTemplate(databaseClient, MySqlDialect.INSTANCE);
    }
}
