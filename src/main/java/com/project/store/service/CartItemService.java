package com.project.store.service;

import com.project.store.model.CartItem;
import com.project.store.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public void save(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }
}
