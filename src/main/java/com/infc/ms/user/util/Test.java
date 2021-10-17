package com.infc.ms.user.util;


import com.infc.ms.user.model.UserModel;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.*;
import io.r2dbc.spi.ConnectionFactoryOptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Test {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "pool")
                .option(ConnectionFactoryOptions.PROTOCOL, "mysql") // driver identifier, PROTOCOL is delegated as DRIVER by the pool.
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 3306)
                .option(ConnectionFactoryOptions.DATABASE, "user_ms")
                .option(ConnectionFactoryOptions.USER, "root")
                .option(ConnectionFactoryOptions.PASSWORD, "root11111111111")
                .build());


        ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(connectionFactory)
                .maxIdleTime(Duration.ofMillis(1000))
                .initialSize(10)
                .maxSize(100)
                .build();
        ConnectionPool pool = new ConnectionPool(configuration);
        Mono<Connection> connection = pool.create();
        Flux.from(connection)
                .flatMap(conn -> conn
                        .createStatement("SELECT * from tm_user1")
                        .execute())
                .flatMap(result -> result
                        .map((row, rowMetadata) ->
                                    UserModel.builder().mobileNumber(row.get("mobile_number", String.class))


                        )).subscribe(s->{
                    System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% "+s);
                });


    }
}