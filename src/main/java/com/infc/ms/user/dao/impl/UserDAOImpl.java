package com.infc.ms.user.dao.impl;

import com.infc.ms.user.dao.UserDAO;
import com.infc.ms.user.model.UserModel;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import javax.transaction.Transactional;
import java.util.Objects;
@Repository
@Log4j2
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

   private final Mutiny.SessionFactory sessionFactory;

    @Override
    public Mono<UserModel> getUserByMobileNumber(String mobileNumber) {
        Objects.requireNonNull(mobileNumber, "mobileNumber can not be null");
        log.info("Fetching user from db: ",UserModel.builder().mobileNumber(mobileNumber));
        System.out.println(UserModel.builder().mobileNumber(mobileNumber));
        if(UserModel.builder().mobileNumber(mobileNumber).build().getUserId()==null)
            return Mono.empty();
        else
            return  Mono.just(UserModel.builder().mobileNumber(mobileNumber).build());
    }


    @Transactional
    @Override
    public Mono<UserModel> saveUser(UserModel userModel) {
        Uni<UserModel> userModelUni=null;
        UserModel saved= UserModel.builder().mobileNumber(userModel.getMobileNumber()).build();
        if (saved.getUserId() == null) {
            userModelUni= sessionFactory.withSession(session ->
                    session.persist(userModel)
                            .chain(session::flush)
                            .replaceWith(userModel)
            );
        } else {
            userModelUni= sessionFactory.withSession(session -> session.merge(userModel).onItem().call(session::flush));
        }
        System.out.println("############### - "+userModelUni.await().indefinitely().getMobileNumber());

        return userModelUni.convert().with(UniReactorConverters.toMono());
    }

}
