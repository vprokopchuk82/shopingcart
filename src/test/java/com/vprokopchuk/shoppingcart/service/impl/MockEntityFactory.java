package com.vprokopchuk.shoppingcart.service.impl;

import com.vprokopchuk.shoppingcart.model.Item;

import java.math.BigDecimal;

public class MockEntityFactory {

    public static Item createItem(String itemId){
        return new Item.Builder()
                .withId(itemId)
                .withName("Java")
                .withDescription("")
                .withPrice(BigDecimal.valueOf(15.5))
                .withQuantity(3)
                .build();



    }

}
