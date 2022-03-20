package com.vprokopchuk.shoppingcart.repository.impl;

import com.vprokopchuk.shoppingcart.model.Item;
import com.vprokopchuk.shoppingcart.model.ItemWrapper;
import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.repository.ItemWrapperRepository;
import com.vprokopchuk.shoppingcart.service.impl.ShoppingCartServiceImpl;
import com.vprokopchuk.shoppingcart.utils.IdGenerator;
import com.vprokopchuk.shoppingcart.utils.IdGeneratorInitializer;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ItemWrapperRepositoryImpl extends AbstractHibernateDao<ItemWrapper> implements ItemWrapperRepository {
    public ItemWrapperRepositoryImpl() {
        super(ItemWrapper.class);
    }

    private static final Logger logger
            = LoggerFactory.getLogger(ItemWrapperRepositoryImpl.class);

    @Override
    public ItemWrapper create(ItemWrapper entity) {
        IdGenerator idGenerator = IdGeneratorInitializer.getInstance();
        idGenerator.generateId(2);
        ItemWrapper itemWrapper = super.create(entity);
        logger.info(String.format("Create %s with id -> %s", ItemWrapper.class.getName(), itemWrapper.getId()));
        return itemWrapper;
    }

    @Override
    public Optional<ItemWrapper> findByShoppingCartAndItemId(String cartId, String itemId){
        List<ItemWrapper> resultList = this.getSessionFactory().getCurrentSession().createQuery("from ItemWrapper iw where iw.cart.id =:cartId and iw.item.id=:itemId", ItemWrapper.class)
                .setParameter("cartId", cartId)
                .setParameter("itemId", itemId)
                .getResultList();
        return CollectionUtils.isNotEmpty(resultList)?Optional.of(resultList.get(0)):Optional.empty();
    }

    @Override
    public List<ItemWrapper> listByShoppingCart(String cartId){
        return this.getSessionFactory().getCurrentSession().createQuery("from ItemWrapper iw where iw.cart.id =:cartId", ItemWrapper.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }
}
