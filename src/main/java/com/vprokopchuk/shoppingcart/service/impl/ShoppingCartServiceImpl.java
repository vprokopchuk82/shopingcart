package com.vprokopchuk.shoppingcart.service.impl;

import com.vprokopchuk.shoppingcart.helper.exception.WrongItemException;
import com.vprokopchuk.shoppingcart.helper.exception.WrongItemQuantityException;
import com.vprokopchuk.shoppingcart.model.Item;
import com.vprokopchuk.shoppingcart.model.ItemWrapper;
import com.vprokopchuk.shoppingcart.model.ShoppingCart;
import com.vprokopchuk.shoppingcart.repository.ItemRepository;
import com.vprokopchuk.shoppingcart.repository.ItemWrapperRepository;
import com.vprokopchuk.shoppingcart.repository.ShoppingCartRepository;
import com.vprokopchuk.shoppingcart.service.ShoppingCartService;
import com.vprokopchuk.shoppingcart.utils.IdGenerator;
import com.vprokopchuk.shoppingcart.utils.IdGeneratorInitializer;
import com.vprokopchuk.shoppingcart.utils.SessionFactoryInitializer;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ItemRepository itemRepository;
    private final ItemWrapperRepository itemWrapperRepository;

    private static final Logger logger
            = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);


    public ShoppingCartServiceImpl(
            ShoppingCartRepository shoppingCartRepository,
            ItemRepository itemRepository,
            ItemWrapperRepository itemWrapperRepository
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.itemRepository = itemRepository;
        this.itemWrapperRepository = itemWrapperRepository;
    }

    @Override
    public ShoppingCart create(ShoppingCart entity) {
        Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
        ShoppingCart shoppingCart = shoppingCartRepository.create(entity);
        transaction.commit();
        return shoppingCart;
    }

    @Override
    public void addItemToCart(String itemId, int quantity) {
        if (checkIfItemAddedToCart(itemId)) {
            changeItemQuantity(itemId, quantity);
        } else {
            IdGenerator generator = IdGeneratorInitializer.getInstance();
            Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
            ShoppingCart shoppingCart = shoppingCartRepository.getOrCreate();

            Item item = Optional.ofNullable(itemRepository.findOne(itemId)).orElseThrow(() -> {
                transaction.rollback();
                return new WrongItemException();
            });

            List<ItemWrapper> itemWrappers = itemWrapperRepository.listByShoppingCart(shoppingCart.getId());
            ItemWrapper itemWrapper = new ItemWrapper.Builder()
                    .withId(generator.generateId(2))
                    .withItem(item)
                    .withShoppingCart(shoppingCart)
                    .withQuantity(quantity)
                    .withTotalAmount(item.getPrice().multiply(BigDecimal.valueOf(quantity)))
                    .build();
            itemWrappers.add(itemWrapper);
            shoppingCart.setItems(itemWrappers);
            shoppingCart.setTotalQuantity(shoppingCart.calculateTotalNumberOfItems());
            shoppingCart.setTotalAmount(shoppingCart.calculateTotalAmount());
            try {
                item.reduceQuantity(quantity);
            } catch (WrongItemQuantityException e) {
                transaction.rollback();
                throw new WrongItemQuantityException();
            }

            itemWrapperRepository.create(itemWrapper);
            shoppingCartRepository.update(shoppingCart);
            itemRepository.update(item);
            transaction.commit();
            logger.info(String.format("Add %s with id -> %s with quantity %d", Item.class.getName(), itemId, quantity));
        }
    }

    @Override
    public void removeItemToCart(String itemId) {
        Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
        ShoppingCart shoppingCart = shoppingCartRepository.getOrCreate();

        Optional<ItemWrapper> itemWrapper = itemWrapperRepository.findByShoppingCartAndItemId(shoppingCart.getId(), itemId);
        List<ItemWrapper> itemWrappers = itemWrapperRepository.listByShoppingCart(shoppingCart.getId());

        if (itemWrapper.isPresent()) {
            List<ItemWrapper> filteredItemCollection = itemWrappers.stream()
                    .filter(i -> !i.equals(itemWrapper.get()))
                    .collect(Collectors.toList());

            shoppingCart.setItems(filteredItemCollection);
            Item item = itemRepository.findOne(itemId);
            itemWrapperRepository.delete(itemWrapper.get());
            shoppingCart.setTotalQuantity(shoppingCart.calculateTotalNumberOfItems());
            shoppingCart.setTotalAmount(shoppingCart.calculateTotalAmount());
            item.increaseQuantity(itemWrapper.get().getQuantity());
            shoppingCartRepository.update(shoppingCart);
            itemRepository.update(item);
            transaction.commit();
            logger.info(String.format("Remove %s with id -> %s", Item.class.getName(), itemId));
        }
    }

    private boolean checkIfItemAddedToCart(String itemId) {
        Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
        ShoppingCart shoppingCart = shoppingCartRepository.getOrCreate();
        Optional<ItemWrapper> itemWrapperOptional = itemWrapperRepository.findByShoppingCartAndItemId(shoppingCart.getId(), itemId);
        transaction.commit();
        return itemWrapperOptional.isPresent();
    }


    @Override
    public void changeItemQuantity(String itemId, int quantity) {
        Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
        ShoppingCart shoppingCart = shoppingCartRepository.getOrCreate();

        Optional<ItemWrapper> itemWrapperOptional = itemWrapperRepository.findByShoppingCartAndItemId(shoppingCart.getId(), itemId);

        if (itemWrapperOptional.isPresent()) {
            ItemWrapper itemWrapper = itemWrapperOptional.get();
            Item item = Optional.ofNullable(itemRepository.findOne(itemId)).orElseThrow(() -> {
                transaction.rollback();
                return new WrongItemException();
            });

            try {
                item.changeQuantity(itemWrapper.getQuantity(), quantity);
            } catch (WrongItemQuantityException e) {
                transaction.rollback();
                throw new WrongItemQuantityException();
            }

            itemWrapper.setQuantity(quantity);
            itemWrapper.calculateTotalAmount(item.getPrice());

            itemWrapperRepository.update(itemWrapper);
            itemRepository.update(item);

            List<ItemWrapper> itemWrappers = itemWrapperRepository.listByShoppingCart(shoppingCart.getId());
            shoppingCart.setItems(itemWrappers);
            shoppingCart.setTotalQuantity(shoppingCart.calculateTotalNumberOfItems());
            shoppingCart.setTotalAmount(shoppingCart.calculateTotalAmount());
            shoppingCartRepository.update(shoppingCart);
            transaction.commit();
            logger.info(String.format("Change %s with id -> %s quantity %d", Item.class.getName(), itemId, quantity));
        }
    }

    @Override
    public ShoppingCart getOrCreate() {
        Transaction transaction = SessionFactoryInitializer.getInstance().getCurrentSession().beginTransaction();
        ShoppingCart shoppingCart = shoppingCartRepository.getOrCreate();
        List<ItemWrapper> itemWrappers = itemWrapperRepository.listByShoppingCart(shoppingCart.getId());
        shoppingCart.setItems(itemWrappers);
        transaction.commit();
        return shoppingCart;
    }
}
