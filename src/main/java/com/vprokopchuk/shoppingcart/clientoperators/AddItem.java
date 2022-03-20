package com.vprokopchuk.shoppingcart.clientoperators;

import com.vprokopchuk.shoppingcart.helper.exception.WrongItemException;
import com.vprokopchuk.shoppingcart.helper.exception.WrongItemQuantityException;
import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.repository.ItemWrapperRepository;
import com.vprokopchuk.shoppingcart.repository.ShoppingCartRepository;
import com.vprokopchuk.shoppingcart.repository.impl.ItemRepositoryImpl;
import com.vprokopchuk.shoppingcart.repository.impl.ItemWrapperRepositoryImpl;
import com.vprokopchuk.shoppingcart.repository.impl.ShoppingCartRepositoryImpl;
import com.vprokopchuk.shoppingcart.service.ShoppingCartService;
import com.vprokopchuk.shoppingcart.service.impl.ShoppingCartServiceImpl;
import com.vprokopchuk.shoppingcart.utils.EngineContext;

import java.util.Scanner;

public class AddItem implements ClientOperators{
    @Override
    public void execute() {
        System.out.println("Check item  by id");
        Scanner scan = new Scanner(System.in);
        String itemId = scan.next();
        System.out.println("Add quantity");
        try {
            int quantity = scan.nextInt();
            EngineContext.getShoppingCartService().addItemToCart(itemId, quantity);
        }
        catch (WrongItemQuantityException e){
            System.out.println("Sorry. But there aren't such quantity of item in stock");
            execute();
        }
        catch (WrongItemException e){
            System.out.println("Sorry. Not correct item id. Please try again");
            execute();
        }
    }
}
