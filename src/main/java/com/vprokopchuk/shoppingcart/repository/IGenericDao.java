package com.vprokopchuk.shoppingcart.repository;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T extends Serializable>{
    void setClazz(Class< T > clazzToSet);

    T findOne(final String id);

    List<T> findAll();

    T create(final T entity);

    void update(final T entity);

    void delete(final T entity);

    void deleteById(final String entityId);
}
