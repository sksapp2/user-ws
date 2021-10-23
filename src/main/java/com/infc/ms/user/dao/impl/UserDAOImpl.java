package com.infc.ms.user.dao.impl;

import com.infc.ms.user.constants.Constants;
import com.infc.ms.user.dao.UserDAO;
import com.infc.ms.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;


@Repository
@Log4j2
@AllArgsConstructor
public class UserDAOImpl implements UserDAO {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<UserModel> getUserByMobileNumber(String mobileNumber, String deviceId, String status) {
        return r2dbcEntityTemplate.select(UserModel.class)
                .from("tm_user")
                .matching(query(where("mobile_number").is(mobileNumber)
                                .and("deviceId").is(deviceId).
                                and("status").is(status)
                        )

                ).one();

    }

    @Override
    public Mono<UserModel> saveUser(UserModel userModel) {
        return r2dbcEntityTemplate.insert(userModel);
    }


}

