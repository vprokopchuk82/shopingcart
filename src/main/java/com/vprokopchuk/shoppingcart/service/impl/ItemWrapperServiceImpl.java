package com.vprokopchuk.shoppingcart.service.impl;

import com.vprokopchuk.shoppingcart.model.ItemWrapper;
import com.vprokopchuk.shoppingcart.repository.ItemWrapperRepository;
import com.vprokopchuk.shoppingcart.service.ItemWrapperService;
import com.vprokopchuk.shoppingcart.utils.EngineContext;
import org.hibernate.Transaction;

import java.util.Optional;

public class ItemWrapperServiceImpl implements ItemWrapperService {
    private final ItemWrapperRepository itemWrapperRepository;

    public ItemWrapperServiceImpl(ItemWrapperRepository itemWrapperRepository) {
        this.itemWrapperRepository = itemWrapperRepository;
    }

    @Override
    public Optional<ItemWrapper> findByShoppingCartAndItemId(String cartId, String itemId) {
        Transaction transaction = EngineContext.getSessionFacory().getCurrentSession().beginTransaction();
        Optional<ItemWrapper> itemWrapper = itemWrapperRepository.findByShoppingCartAndItemId(cartId, itemId);
        transaction.commit();
        return itemWrapper;
    }


}
