package com.vprokopchuk.shoppingcart.repository.impl;

import com.vprokopchuk.shoppingcart.model.Item;
import com.vprokopchuk.shoppingcart.model.ItemWrapper;
import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.service.impl.ShoppingCartServiceImpl;
import com.vprokopchuk.shoppingcart.utils.IdGenerator;
import com.vprokopchuk.shoppingcart.utils.IdGeneratorInitializer;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ItemRepositoryImpl extends AbstractHibernateDao<Item> implements ItemRepository {
    public ItemRepositoryImpl() {
        super(Item.class);
    }
    private static final Logger logger
            = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);



    @Override
    public Item create(Item entity) {
        entity.setId(IdGeneratorInitializer.getInstance().generateId(2));
        Item item = super.create(entity);
        logger.info(String.format("Create %s with id -> %s", Item.class.getName(), item.getId()));
        return item;
    }

    @Override
    public List<Item> findAll() {
        return super.findAll();
    }

    @Override
    public void update(Item entity) {
        super.update(entity);
        logger.info(String.format("Update %s with id -> %s", Item.class.getName(), entity.getId()));
    }
}
