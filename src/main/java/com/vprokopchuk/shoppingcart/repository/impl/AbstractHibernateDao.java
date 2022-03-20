package com.vprokopchuk.shoppingcart.repository.impl;

import com.vprokopchuk.shoppingcart.repository.IGenericDao;
import com.vprokopchuk.shoppingcart.utils.SessionFactoryInitializer;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateDao<T extends Serializable> implements IGenericDao<T> {
        private Class<T> clazz;

    public AbstractHibernateDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected SessionFactory sessionFactory = SessionFactoryInitializer.getInstance();

        public void setClazz(final Class<T> clazzToSet) {
            clazz = clazzToSet;
        }

        public T findOne(final String id) {
            return (T) sessionFactory.getCurrentSession().get(clazz, id);
        }

        public List<T> findAll() {
            return sessionFactory.getCurrentSession().createQuery("from " + clazz.getName(), clazz).list();
        }

        public T create(final T entity) {
            sessionFactory.getCurrentSession().saveOrUpdate(entity);
            return entity;
        }



        public void update(final T entity) {
            sessionFactory.getCurrentSession().saveOrUpdate(entity);
        }

        public void delete(final T entity) {
            sessionFactory.getCurrentSession().delete(entity);
        }

        public void deleteById(final String entityId) {
            final T entity = findOne(entityId);
            delete(entity);
        }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}


