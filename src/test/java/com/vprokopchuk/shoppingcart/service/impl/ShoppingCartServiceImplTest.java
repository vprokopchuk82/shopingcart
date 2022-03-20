package com.vprokopchuk.shoppingcart.service.impl;

import com.vprokopchuk.shoppingcart.helper.exception.WrongItemQuantityException;
import com.vprokopchuk.shoppingcart.model.Item;
import com.vprokopchuk.shoppingcart.model.ItemWrapper;
import com.vprokopchuk.shoppingcart.model.ShoppingCart;
import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.repository.ItemWrapperRepository;
import com.vprokopchuk.shoppingcart.repository.ShoppingCartRepository;
import com.vprokopchuk.shoppingcart.service.ShoppingCartService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(JUnit4.class)
public class ShoppingCartServiceImplTest{
    ShoppingCartRepository shoppingCartRepository = Mockito.mock(ShoppingCartRepository.class);
    ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    ItemWrapperRepository itemWrapperRepository = Mockito.mock(ItemWrapperRepository.class);
    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(shoppingCartRepository, itemRepository, itemWrapperRepository);

    @Test
    public void addItemToCartTest(){
        String itemId = "11111111111";
        String shoppingCartId = "11111111111";
        ShoppingCart shoppingCart = new ShoppingCart();
        Item item = MockEntityFactory.createItem(itemId);


        shoppingCart.setId(shoppingCartId);
        Mockito.when(shoppingCartRepository.getOrCreate()).thenReturn(shoppingCart);
        Mockito.when(itemRepository.findOne(itemId)).thenReturn(item);
        Mockito.when(itemWrapperRepository.listByShoppingCart(shoppingCart.getId())).thenReturn(new ArrayList<>());

        Mockito.when(itemWrapperRepository.listByShoppingCart(shoppingCart.getId())).thenReturn(new ArrayList<>());
        Mockito.when(itemWrapperRepository.create(Mockito.any(ItemWrapper.class))).thenReturn(null);
        Mockito.doNothing().when(shoppingCartRepository).update(shoppingCart);
        Mockito.doNothing().when(itemRepository).update(item);
        shoppingCartService.addItemToCart(itemId, 2);

        Assert.assertEquals(BigDecimal.valueOf(15.5*2.0), shoppingCart.getTotalAmount());
        Assert.assertEquals(2, shoppingCart.getTotalQuantity());
        Assert.assertEquals(1, item.getQuantity());

    }

    @Test(expected = WrongItemQuantityException.class)
    public void addItemWithMoreQuantityThanExistTest(){
        String itemId = "11111111111";
        String shoppingCartId = "11111111111";
        int quantity = 5;
        ShoppingCart shoppingCart = new ShoppingCart();
        Item item = MockEntityFactory.createItem(itemId);


        shoppingCart.setId(shoppingCartId);
        Mockito.when(shoppingCartRepository.getOrCreate()).thenReturn(shoppingCart);
        Mockito.when(itemRepository.findOne(itemId)).thenReturn(item);
        Mockito.when(itemWrapperRepository.listByShoppingCart(shoppingCart.getId())).thenReturn(new ArrayList<>());

        Mockito.when(itemWrapperRepository.listByShoppingCart(shoppingCart.getId())).thenReturn(new ArrayList<>());
        Mockito.when(itemWrapperRepository.create(Mockito.any(ItemWrapper.class))).thenReturn(null);
        Mockito.doNothing().when(shoppingCartRepository).update(shoppingCart);
        Mockito.doNothing().when(itemRepository).update(item);
        shoppingCartService.addItemToCart(itemId, quantity);
    }




}