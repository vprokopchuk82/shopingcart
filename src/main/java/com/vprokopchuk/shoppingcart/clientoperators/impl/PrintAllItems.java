package com.vprokopchuk.shoppingcart.clientoperators.impl;

import com.vprokopchuk.shoppingcart.clientoperators.ClientOperators;
import com.vprokopchuk.shoppingcart.model.Item;
import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.repository.impl.ItemRepositoryImpl;
import com.vprokopchuk.shoppingcart.service.ItemService;
import com.vprokopchuk.shoppingcart.service.impl.ItemServiceImpl;
import com.vprokopchuk.shoppingcart.utils.EngineContext;

import java.util.List;

public class PrintAllItems implements ClientOperators {
    @Override
    public void execute() {
        List<Item> all = EngineContext.getItemService().findAll();
        all.forEach(i-> System.out.println(i.toString()));
    }
}
