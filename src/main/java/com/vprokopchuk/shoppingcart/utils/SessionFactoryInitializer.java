package com.vprokopchuk.shoppingcart.utils;

import org.hibernate.SessionFactory;

import java.util.Objects;

public class SessionFactoryInitializer {
    private static SessionFactory sessionFactory=null;

    private SessionFactoryInitializer() {
    }

    public static SessionFactory getInstance(){
        if (Objects.isNull(sessionFactory)){
            sessionFactory = HibernateUtil.getSessionFactory();
        }
        return sessionFactory;
    }


}
