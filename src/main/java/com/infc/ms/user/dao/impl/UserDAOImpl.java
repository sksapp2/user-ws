package com.infc.ms.user.dao.impl;
//https://quarkus.io/guides/reactive-sql-clients
import com.infc.ms.user.config.MySqlClientConfig;
import com.infc.ms.user.constants.Constants;
import com.infc.ms.user.dao.UserDAO;
import com.infc.ms.user.model.UserModel;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.PreparedQuery;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;


@Repository
@Log4j2
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final MySqlClientConfig mySqlClientConfig;

    @Override
    public Mono<UserModel> getUserByMobileNumber(String mobileNumber, String deviceId,String status) {
        Pool mySQLPool = mySqlClientConfig.mySQLPool();
        log.info("POOL siz : {}",mySQLPool.size());
        PreparedQuery<RowSet<Row>> preparedStatement = mySQLPool.preparedQuery(Constants.FETCH_USER);
        Uni<UserModel> uniUserModel = preparedStatement.execute(
                        Tuple.of(mobileNumber, deviceId,status)).onItem().transform(rows -> {
                    for (Row r : rows) {
                        return UserModel.builder().
                                mobileNumber(r.getString("mobile_number")).
                                build();
                    }
                    return null;
                })
                .onSubscription()
                .invoke(() -> {
                            log.info("Completed");
                            mySQLPool.close();
                        }
                );
        log.info("Siz after: {}",mySQLPool.size());
        return uniUserModel.convert().with(UniReactorConverters.toMono());
    }


    @Override
    //user_id,country_phone_code,created_date_time,device_id,mobile_number,public_key,status
    public Mono<UserModel> saveUser(UserModel userModel) throws Throwable {
        Pool mySQLPool = mySqlClientConfig.mySQLPool();
        Uni<String> userId= mySQLPool.withTransaction(conn -> {
            Uni<String> uniUserId= conn.preparedQuery(Constants.INSERT_USER).
                    execute(Tuple.from(Arrays.asList(userModel.getUserId(),
                            userModel.getCountryPhoneCode(),
                            userModel.getCreatedDateTime(),
                            userModel.getDeviceId(),
                            userModel.getMobileNumber(),
                            userModel.getPublicKey(),
                            userModel.getStatus())
                    )).log().onItem().
                    transform(pgRowSet -> pgRowSet.iterator().next().getString("id"));
            conn.close();
            return uniUserId;
        }).onSubscription().invoke(()->{
            mySQLPool.close();
        });
        userId.subscribe().with(e->{
            System.out.println(e);
        });
        log.info("saved user in db");

        return Mono.empty();
    }


}

