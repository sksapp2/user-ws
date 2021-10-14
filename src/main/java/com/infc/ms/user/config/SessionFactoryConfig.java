package com.infc.ms.user.config;

import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class SessionFactoryConfig {
    @Bean
    public Mutiny.SessionFactory sessionFactory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("user_db");
        Mutiny.SessionFactory sessionFactory = emf.unwrap(Mutiny.SessionFactory.class);
        return sessionFactory;
    }
}
