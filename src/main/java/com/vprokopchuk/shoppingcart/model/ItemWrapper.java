package com.vprokopchuk.shoppingcart.model;

import com.vprokopchuk.shoppingcart.model.dto.ItemDto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "item_wrapper")
public class ItemWrapper implements Serializable {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name="item_id", nullable=false)
    private Item item;

    @Column(name = "quantity")
    private int quantity;
    @Column(name = "totalAmount")
    private BigDecimal totalAmount;


    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private ShoppingCart cart;

    public ItemWrapper() {
    }

    public ItemWrapper(
            String id,
            Item item,
            int quantity,
            BigDecimal totalAmount,
            ShoppingCart shoppingCart

    ) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.cart = shoppingCart;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public void calculateTotalAmount(BigDecimal price){
        this.totalAmount = price.multiply(BigDecimal.valueOf(quantity));

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemWrapper that = (ItemWrapper) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(item, that.item) &&
                Objects.equals(cart, that.cart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, item, cart);
    }

    @Override
    public String toString() {
        ItemDto itemDto = new ItemDto(item);

        return "ItemWrapper{" +
                ", item=" + itemDto +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                '}';
    }

    public static class Builder{
        private String id;
        private Item item;
        private int quantity;
        private BigDecimal totalAmount;
        private ShoppingCart shoppingCart;

        public ItemWrapper.Builder withId(String id){
            this.id = id;
            return this;
        }

        public ItemWrapper.Builder withItem(Item item){
            this.item = item;
            return this;
        }

        public ItemWrapper.Builder withQuantity(int quantity){
            this.quantity = quantity;
            return this;
        }

        public ItemWrapper.Builder withTotalAmount(BigDecimal totalAmount){
            this.totalAmount = totalAmount;
            return this;
        }

        public ItemWrapper.Builder withShoppingCart(ShoppingCart shoppingCart){
            this.shoppingCart = shoppingCart;
            return this;
        }

        public ItemWrapper build(){
            return new ItemWrapper(
                    id,
                    item,
                    quantity,
                    totalAmount,
                    shoppingCart
            );
        }

    }
}
