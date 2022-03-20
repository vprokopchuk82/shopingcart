package com.vprokopchuk.shoppingcart.service;

import com.vprokopchuk.shoppingcart.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart create(ShoppingCart entity);

    void addItemToCart(String itemId, int quantity);

    void removeItemToCart(String itemId);

    ShoppingCart getOrCreate();

    void changeItemQuantity(String itemId, int quantity);
}
