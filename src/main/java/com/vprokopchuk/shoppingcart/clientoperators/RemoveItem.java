package com.vprokopchuk.shoppingcart.clientoperators;

import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.repository.ItemWrapperRepository;
import com.vprokopchuk.shoppingcart.repository.ShoppingCartRepository;
import com.vprokopchuk.shoppingcart.repository.impl.ItemRepositoryImpl;
import com.vprokopchuk.shoppingcart.repository.impl.ItemWrapperRepositoryImpl;
import com.vprokopchuk.shoppingcart.repository.impl.ShoppingCartRepositoryImpl;
import com.vprokopchuk.shoppingcart.service.ShoppingCartService;
import com.vprokopchuk.shoppingcart.service.impl.ShoppingCartServiceImpl;

import java.util.Scanner;

public class RemoveItem implements ClientOperators {
    private final ShoppingCartRepository shoppingCartRepository = new ShoppingCartRepositoryImpl();
    private final ItemRepository itemRepository = new ItemRepositoryImpl();
    private final ItemWrapperRepository itemWrapperRepository = new ItemWrapperRepositoryImpl();
    private final ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(shoppingCartRepository,itemRepository, itemWrapperRepository);

    @Override
    public void execute() {
        System.out.println("Check item  by id");
        Scanner scan = new Scanner(System.in);
        String itemId = scan.next();
        shoppingCartService.removeItemToCart(itemId);
    }
}
