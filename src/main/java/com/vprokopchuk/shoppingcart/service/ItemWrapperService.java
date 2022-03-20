package com.vprokopchuk.shoppingcart.service;

import com.vprokopchuk.shoppingcart.model.ItemWrapper;

import java.util.Optional;

public interface ItemWrapperService {
    Optional<ItemWrapper> findByShoppingCartAndItemId(String cartId, String itemId);
}
