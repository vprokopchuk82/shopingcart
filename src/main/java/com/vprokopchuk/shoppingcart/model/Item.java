package com.vprokopchuk.shoppingcart.model;

import com.vprokopchuk.shoppingcart.helper.exception.WrongItemQuantityException;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "tag")
    private ItemTag tag;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "quantity")
    private int quantity;

    public Item(String id, String name, String description, ItemTag tag, BigDecimal price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.price = price;
        this.quantity = quantity;
    }

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemTag getTag() {
        return tag;
    }

    public void setTag(ItemTag tag) {
        this.tag = tag;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void reduceQuantity(int count){
        if (this.quantity>=count) {
            this.quantity -= count;
        }
        else throw new WrongItemQuantityException();
    }

    public void increaseQuantity(int count){
        this.quantity += count;
    }

    public void changeQuantity(int quantityFromShoppingCart, int newQuantity){
        if (newQuantity>=(this.quantity+quantityFromShoppingCart)){
            throw new WrongItemQuantityException();
        }else {
            this.quantity = this.quantity + quantityFromShoppingCart - newQuantity;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                tag == item.tag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, tag);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tag=" + tag +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public static class Builder {
        private String id;
        private String name;
        private String description;
        private ItemTag tag;
        private BigDecimal price;
        private int quantity;

        public Item.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Item.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Item.Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Item.Builder withTag(ItemTag tag) {
            this.tag = tag;
            return this;
        }

        public Item.Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Item.Builder withQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }



        public Item build(){
            return new Item(
                    id,
                    name,
                    description,
                    tag,
                    price,
                    quantity
            );


        }
    }
}
