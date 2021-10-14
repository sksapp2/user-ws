package com.infc.ms.user.dao.impl;

import com.infc.ms.user.dao.UserDAO;
import com.infc.ms.user.model.UserModel;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    Mutiny.SessionFactory sessionFactory;
    @Override
    public Mono<UserModel> getUserByMobileNumber(String mobileNumber) {
        Objects.requireNonNull(mobileNumber, "mobileNumber can not be null");
        return Mono.just(UserModel.builder().mobileNumber(mobileNumber).build());
    }


    @Transactional
    @Override
    public Mono<UserModel> saveUser(UserModel userModel) {

        Uni<UserModel> userModelUni=null;
        if (userModel.getMobileNumber() == null) {
            userModelUni= sessionFactory.withSession(session ->
                    session.persist(userModel)
                            .chain(session::flush)
                            .replaceWith(userModel)
            );
        } else {
            userModelUni= sessionFactory.withSession(session -> session.merge(userModel).onItem().call(session::flush));
        }
        return userModelUni.convert().with(UniReactorConverters.toMono());
    }

}
