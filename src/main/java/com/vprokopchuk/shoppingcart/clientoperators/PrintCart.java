package com.vprokopchuk.shoppingcart.clientoperators;

import com.vprokopchuk.shoppingcart.model.ShoppingCart;
import com.vprokopchuk.shoppingcart.utils.EngineContext;

public class PrintCart implements ClientOperators{
    @Override
    public void execute() {
        ShoppingCart shoppingCart = EngineContext.getShoppingCartService().getOrCreate();
        System.out.println(shoppingCart.toString());
    }
}
