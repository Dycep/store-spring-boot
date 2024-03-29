package com.project.store.service;

import com.project.store.controller.dto.CartRequest;
import com.project.store.model.Cart;
import com.project.store.model.CartItem;
import com.project.store.model.Item;
import com.project.store.model.User;
import com.project.store.repository.CartItemRepository;
import com.project.store.repository.CartRepository;
import com.project.store.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemService itemService;
    private final CartItemService cartItemService;

    public Integer saveCart(CartRequest request){
        Cart cart = new Cart();
        cart.setUser(request.getUser());
        cart.setComment(request.getComment());
        cartRepository.save(cart);
        Map<Long, Integer> productIdProductCount = getProductIdProductCountMap(request);

        for (Map.Entry<Long, Integer> entry : productIdProductCount.entrySet()) {
            Long k = entry.getKey();
            Integer v = entry.getValue();
            Item item = itemService.getItem(k);
            CartItem p = new CartItem();
            p.setItem(item);
            p.setAmount(v);
            p.setCart(cart);
            cartItemService.save(p);
        }

        return cart.getId();
    }

    private Map<Long, Integer> getProductIdProductCountMap(CartRequest request) {
        Map<Long, Integer> productIdProductCount = new HashMap<>();
        for (Item it : request.getItems()) {
            if (productIdProductCount.containsKey(it.getId())) {
                Integer productCount = productIdProductCount.get(it.getId());
                productCount = productCount + 1;
                productIdProductCount.put(it.getId(), productCount);
            } else {
                productIdProductCount.put(it.getId(), 1);
            }
        }
        return productIdProductCount;
    }
}
