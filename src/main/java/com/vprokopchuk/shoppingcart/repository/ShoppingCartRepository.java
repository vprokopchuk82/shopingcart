package com.vprokopchuk.shoppingcart.repository;

import com.vprokopchuk.shoppingcart.model.ShoppingCart;

public interface ShoppingCartRepository extends IGenericDao<ShoppingCart>{
    ShoppingCart getOrCreate();
}
