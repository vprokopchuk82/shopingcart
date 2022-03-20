package com.vprokopchuk.shoppingcart.service;

import com.vprokopchuk.shoppingcart.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();

    Item create(Item entity);

    List<Item> createList(List<Item> itemList);

    void deleteList(List<Item> itemList);
}
