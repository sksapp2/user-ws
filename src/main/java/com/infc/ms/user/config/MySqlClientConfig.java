package com.infc.ms.user.config;


import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySqlClientConfig {


    @Bean
    public Pool mySQLPool() {
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
                .setReconnectInterval(1000).setCachePreparedStatements(true);

        return Pool.pool(connectOptions);
    }
}
