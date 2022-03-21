package com.vprokopchuk.shoppingcart;


import com.vprokopchuk.shoppingcart.clientoperators.*;
import com.vprokopchuk.shoppingcart.clientoperators.impl.ClientOperatorsFabricImpl;
import com.vprokopchuk.shoppingcart.model.Item;
import com.vprokopchuk.shoppingcart.service.ItemService;
import com.vprokopchuk.shoppingcart.utils.EngineContext;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Shop{

    private static final ClientOperatorsFabric fabric = new ClientOperatorsFabricImpl();
    public static void main (String[] args)
    {

        populateItems();
        Scanner scan = new Scanner(System.in);
        String keepShopping = "y";
        do
        {

            System.out.print (
                    "List of Items -> 1;\n " +
                    "Add Item to Shopping Cart -> 2;\n" +
                    " Remove Item from Shopping Cart -> 3;\n" +
                            " Change Item quantity - >4;\n"+
                            " Print Shopping Cart - >5;\n"
            );
            String choice = scan.next();
            ClientOperators operator = fabric.getOperator(choice);
            operator.execute();
            keepShopping = isKeepShopping();
        }
        while (!keepShopping.equalsIgnoreCase("n"));

    }


    private  static String isKeepShopping(){
        Scanner scan = new Scanner(System.in);
        System.out.print ("Continue shopping (y/n)? ");
        String keepShopping = scan.next();
        if (!keepShopping.equals("y")&&!keepShopping.equals("n")){
            isKeepShopping();
        }
        return keepShopping;
    }


    private static void populateItems(){
        ItemService itemService = EngineContext.getItemService();
        List<Item> all = itemService.findAll();
        List<Item> items;
        if (CollectionUtils.isNotEmpty(all)){
            items = all;
        }
        else {
            Item item1 = new Item.Builder()
                    .withName("Java")
                    .withDescription("")
                    .withPrice(BigDecimal.valueOf(15.5))
                    .withQuantity(3)
                    .build();

            Item item2 = new Item.Builder()
                    .withName("Java2")
                    .withDescription("")
                    .withPrice(BigDecimal.valueOf(15.5))
                    .withQuantity(3)
                    .build();

            Item item3 = new Item.Builder()
                    .withName("Java3")
                    .withDescription("")
                    .withPrice(BigDecimal.valueOf(15.5))
                    .withQuantity(3)
                    .build();

            Item item4 = new Item.Builder()
                    .withName("Java4")
                    .withDescription("")
                    .withPrice(BigDecimal.valueOf(15.5))
                    .withQuantity(3)
                    .build();

            items = Arrays.asList(item1, item2, item3, item4);
            itemService.createList(items);
        }
    }


}