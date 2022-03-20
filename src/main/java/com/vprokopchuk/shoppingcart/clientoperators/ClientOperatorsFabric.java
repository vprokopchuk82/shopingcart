package com.vprokopchuk.shoppingcart.clientoperators;

public class ClientOperatorsFabric {

    public ClientOperators getOperator(String userInput) {
        switch (userInput) {
            case "1":
                return new PrintAllItems();

            case "2":
                return new AddItem();

            case "3":
                return new RemoveItem();

            case "4":
                return new ChangeItemCount();

            case "5":
                return new PrintCart();

            default:
                return new PrintAllItems();
        }


    }

}
