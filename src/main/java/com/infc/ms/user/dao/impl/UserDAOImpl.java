package com.infc.ms.user.dao.impl;

import com.infc.ms.user.dao.UserDAO;
import com.infc.ms.user.model.UserModel;

import com.infc.ms.user.model.UserModel_;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.converters.uni.UniReactorConverters;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        return getUserModelUni(mobileNumber).convert().with(UniReactorConverters.toMono());
    }

    private Uni<UserModel> getUserModelUni(String mobileNumber) {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<UserModel> query = cb.createQuery(UserModel.class);
        Root<UserModel> root = query.from(UserModel.class);
        Predicate predicateForMobileNumber
                = cb.equal(root.get("mobileNumber"), mobileNumber);
        query.where(predicateForMobileNumber);
        return this.sessionFactory.withSession(session ->
                session.createQuery(query).getResultList().onItem().transform(userModels ->
                     userModels.size()==0?null:userModels.get(0)
                ).onItem().call(session::flush));


    }



    @Transactional
    @Override
    public Mono<UserModel> saveUser(UserModel userModel) {
        Uni<UserModel> userModelUni = sessionFactory.withSession(session -> session.merge(userModel).
                onItem().call(session::flush));
        return userModelUni.convert().with(UniReactorConverters.toMono());
    }

}
