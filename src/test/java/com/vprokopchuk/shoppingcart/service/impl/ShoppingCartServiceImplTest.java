package com.vprokopchuk.shoppingcart.service.impl;

import com.vprokopchuk.shoppingcart.helper.exception.WrongItemQuantityException;
import com.vprokopchuk.shoppingcart.model.Item;
import com.vprokopchuk.shoppingcart.model.ItemWrapper;
import com.vprokopchuk.shoppingcart.model.ShoppingCart;
import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.repository.ItemWrapperRepository;
import com.vprokopchuk.shoppingcart.repository.ShoppingCartRepository;
import com.vprokopchuk.shoppingcart.service.ShoppingCartService;
import com.vprokopchuk.shoppingcart.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.transaction.internal.TransactionImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "com.sun.org.apache.xalan.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(HibernateUtil.class)
public class ShoppingCartServiceImplTest{
    ShoppingCartRepository shoppingCartRepository = Mockito.mock(ShoppingCartRepository.class);
    ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    ItemWrapperRepository itemWrapperRepository = Mockito.mock(ItemWrapperRepository.class);
    ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl(shoppingCartRepository, itemRepository, itemWrapperRepository);


    SessionFactory sf;
    SessionFactoryImplementor sfi;
    @Mock
    Session session;


    @Before
    public void setUp() {
        sf = Mockito.mock(SessionFactory.class, withSettings().extraInterfaces(SessionFactoryImplementor.class));
        sfi = (SessionFactoryImplementor) sf;
        when(sf.openSession()).thenReturn(session);
        when(sf.getCurrentSession()).thenReturn(session);
        TransactionImpl mockTransaction = Mockito.mock(TransactionImpl.class);
        Mockito.when(session.beginTransaction()).thenReturn(mockTransaction);
        Mockito.doNothing().when(mockTransaction).commit();
        Mockito.doNothing().when(mockTransaction).begin();
        Mockito.doNothing().when(mockTransaction).rollback();

        PowerMockito.mockStatic(HibernateUtil.class);
        PowerMockito.when(HibernateUtil.buildSessionFactory()).thenReturn(sf);
    }




    @Test
    public void addItemToCartTest(){
        String itemId = "11111111111";
        String shoppingCartId = "11111111111";
        ShoppingCart shoppingCart = new ShoppingCart();
        Item item = MockEntityFactory.createItem(itemId);


        shoppingCart.setId(shoppingCartId);
        when(shoppingCartRepository.getOrCreate()).thenReturn(shoppingCart);
        when(itemRepository.findOne(itemId)).thenReturn(item);
        when(itemWrapperRepository.listByShoppingCart(shoppingCart.getId())).thenReturn(new ArrayList<>());
        when(itemWrapperRepository.findByShoppingCartAndItemId(shoppingCart.getId(), itemId)).thenReturn(Optional.empty());

        when(itemWrapperRepository.listByShoppingCart(shoppingCart.getId())).thenReturn(new ArrayList<>());
        when(itemWrapperRepository.create(Mockito.any(ItemWrapper.class))).thenReturn(null);
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
        when(shoppingCartRepository.getOrCreate()).thenReturn(shoppingCart);
        when(itemRepository.findOne(itemId)).thenReturn(item);
        when(itemWrapperRepository.listByShoppingCart(shoppingCart.getId())).thenReturn(new ArrayList<>());
        when(itemWrapperRepository.findByShoppingCartAndItemId(shoppingCart.getId(), itemId)).thenReturn(Optional.empty());

        when(itemWrapperRepository.listByShoppingCart(shoppingCart.getId())).thenReturn(new ArrayList<>());
        when(itemWrapperRepository.create(Mockito.any(ItemWrapper.class))).thenReturn(null);
        Mockito.doNothing().when(shoppingCartRepository).update(shoppingCart);
        Mockito.doNothing().when(itemRepository).update(item);
        shoppingCartService.addItemToCart(itemId, quantity);
    }




}