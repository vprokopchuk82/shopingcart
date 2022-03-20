package com.vprokopchuk.shoppingcart.utils;

import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.repository.ItemWrapperRepository;
import com.vprokopchuk.shoppingcart.repository.ShoppingCartRepository;
import com.vprokopchuk.shoppingcart.repository.impl.ItemRepositoryImpl;
import com.vprokopchuk.shoppingcart.repository.impl.ItemWrapperRepositoryImpl;
import com.vprokopchuk.shoppingcart.repository.impl.ShoppingCartRepositoryImpl;
import com.vprokopchuk.shoppingcart.service.ItemService;
import com.vprokopchuk.shoppingcart.service.ShoppingCartService;
import com.vprokopchuk.shoppingcart.service.impl.ItemServiceImpl;
import com.vprokopchuk.shoppingcart.service.impl.ShoppingCartServiceImpl;

import java.util.Objects;

public class EngineContext {
    private static ShoppingCartRepository shoppingCartRepository;
    private static ItemRepository itemRepository;
    private static ItemWrapperRepository itemWrapperRepository;
    private static ShoppingCartService shoppingCartService;
    private static ItemService itemService;


    private EngineContext() {
    }

    public static ShoppingCartRepository getShoppingCartRepository(){
        if (Objects.nonNull(shoppingCartRepository)){
            return shoppingCartRepository;
        }
        else synchronized (EngineContext.class){
            return shoppingCartRepository = new ShoppingCartRepositoryImpl();
        }
    }

    public static ItemRepository getItemRepository(){
        if (Objects.nonNull(itemRepository)){
            return itemRepository;
        }
        else synchronized (EngineContext.class){
            return itemRepository = new ItemRepositoryImpl();
        }
    }

    public static ItemWrapperRepository getItemWrapperRepository(){
        if (Objects.nonNull(itemWrapperRepository)){
            return itemWrapperRepository;
        }
        else synchronized (EngineContext.class){
            return itemWrapperRepository = new ItemWrapperRepositoryImpl();
        }
    }

    public static ItemService getItemService(){
        if (Objects.nonNull(itemService)){
            return itemService;
        }
        else synchronized (EngineContext.class){
            return itemService = new ItemServiceImpl(getItemRepository());
        }
    }

    public static ShoppingCartService getShoppingCartService(){
        if (Objects.nonNull(shoppingCartService)){
            return shoppingCartService;
        }
        else synchronized (EngineContext.class){
            return shoppingCartService = new ShoppingCartServiceImpl(
                    getShoppingCartRepository(),
                    getItemRepository(),
                    getItemWrapperRepository()
            );
        }
    }

}
