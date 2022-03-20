package com.vprokopchuk.shoppingcart.model;

import com.vprokopchuk.shoppingcart.helper.exception.WrongItemQuantityException;
import com.vprokopchuk.shoppingcart.service.impl.MockEntityFactory;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ItemTest{
    @Test
    public void testQuantityTest(){
        String itemId = "11111111";
        Item item = MockEntityFactory.createItem(itemId);
        item.changeQuantity(0, 2);
        Assert.assertEquals(1, item.getQuantity());
    }

    @Test(expected = WrongItemQuantityException.class)
    public void changeItemQuantityWithMoreQuantityThanExist(){
        String itemId = "11111111";
        Item item = MockEntityFactory.createItem(itemId);
        item.changeQuantity(0, 5);
    }



}