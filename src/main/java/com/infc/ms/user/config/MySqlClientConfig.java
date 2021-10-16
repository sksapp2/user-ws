package com.infc.ms.user.config;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mysqlclient.MySQLConnectOptions;

import io.vertx.sqlclient.PoolOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Log4j2
public class MySqlClientConfig {
    @Bean
    public Pool mySQLPool() {
        log.info("db-----------------init");
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(3306)
                .setHost("localhost")
                .setDatabase("user_ms")
                .setUser("root")
                .setPassword("root");
        PoolOptions poolOptions = new PoolOptions();
        poolOptions.setMaxSize(20);
        connectOptions.setCharset("utf8");
        connectOptions
                .setReconnectAttempts(2)
                .setReconnectInterval(10).
                setCachePreparedStatements(true)
                .setPreparedStatementCacheMaxSize(250)
                .setPreparedStatementCacheSqlLimit(2048);
        return Pool.pool(connectOptions);
    }
}
