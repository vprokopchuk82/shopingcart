package com.vprokopchuk.shoppingcart.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @OneToMany(mappedBy="cart", fetch = FetchType.LAZY)
    private List<ItemWrapper> items = new ArrayList<>();

    @Column(name = "total_quantity")
    @ColumnDefault("0")
    private int totalQuantity;


    @Column(name = "total_amount")
    @ColumnDefault("0")
    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ItemWrapper> getItems() {
        return items;
    }

    public void setItems(List<ItemWrapper> items) {
        this.items = items;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalCount) {
        this.totalQuantity = totalCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addItem(ItemWrapper item){
        this.items.add(item);
    }

    public void removeItem(ItemWrapper item){
        this.items.remove(item);
    }

    public int calculateTotalNumberOfItems(){
        return items.stream().mapToInt(ItemWrapper::getQuantity).sum();

    }

    public BigDecimal calculateTotalAmount(){
        return items.stream()
                .map(i -> (i.getItem().getPrice().multiply(BigDecimal.valueOf(i.getQuantity()))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id='" + id + '\'' +
                ", items=" + items +
                ", totalQuantity=" + totalQuantity +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
