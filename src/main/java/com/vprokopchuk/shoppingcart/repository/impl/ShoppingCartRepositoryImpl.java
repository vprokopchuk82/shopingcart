package com.vprokopchuk.shoppingcart.repository.impl;

import com.vprokopchuk.shoppingcart.model.ShoppingCart;
import com.vprokopchuk.shoppingcart.repository.ShoppingCartRepository;
import com.vprokopchuk.shoppingcart.utils.IdGenerator;
import com.vprokopchuk.shoppingcart.utils.IdGeneratorInitializer;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ShoppingCartRepositoryImpl extends AbstractHibernateDao<ShoppingCart> implements ShoppingCartRepository {
    public ShoppingCartRepositoryImpl() {
        super(ShoppingCart.class);
    }
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartRepositoryImpl.class);

    @Override
    public ShoppingCart create(ShoppingCart entity) {
        IdGenerator generator = IdGeneratorInitializer.getInstance();
        entity.setId(generator.generateId(2));
        ShoppingCart shoppingCart = super.create(entity);
        logger.info(String.format("Create %s with id -> %s", ShoppingCart.class.getName(), shoppingCart.getId()));
        return shoppingCart;
    }

    @Override
    public ShoppingCart getOrCreate() {
        List<ShoppingCart> all = findAll();
        if (CollectionUtils.isNotEmpty(all)){
            return all.get(0);
        }
        else {
            ShoppingCart shoppingCart = new ShoppingCart();
            return create(shoppingCart);
        }
    }

    @Override
    public void update(ShoppingCart entity) {
        super.update(entity);
        logger.info(String.format("Create %s with id -> %s", ShoppingCart.class.getName(), entity.getId()));
    }
}
