package com.vprokopchuk.shoppingcart.repository;

import com.vprokopchuk.shoppingcart.model.Item;
import com.vprokopchuk.shoppingcart.model.ItemWrapper;

import java.util.List;
import java.util.Optional;

public interface ItemWrapperRepository extends IGenericDao<ItemWrapper> {
    Optional<ItemWrapper> findByShoppingCartAndItemId(String cartId, String itemId);

    List<ItemWrapper> listByShoppingCart(String cartId);
}
