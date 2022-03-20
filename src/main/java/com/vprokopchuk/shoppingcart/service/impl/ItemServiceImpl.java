package com.vprokopchuk.shoppingcart.service.impl;

import com.vprokopchuk.shoppingcart.model.Item;
import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.service.ItemService;
import com.vprokopchuk.shoppingcart.utils.EngineContext;
import com.vprokopchuk.shoppingcart.utils.SessionFactoryInitializer;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAll() {
        Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
        List<Item> all = itemRepository.findAll();
        transaction.commit();
        return all;

    }


    @Override
    public Item create(Item entity) {
        Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
        Item item = itemRepository.create(entity);
        transaction.commit();
        return item;
    }

    @Override
    public List<Item> createList(List<Item> itemList) {
        Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
        List<Item> collect = itemList.stream().map(itemRepository::create).collect(Collectors.toList());
        transaction.commit();
        return collect;
    }

    @Override
    public void deleteList(List<Item> itemList) {
        Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
        itemList.forEach(itemRepository::delete);
        transaction.commit();
    }
}
