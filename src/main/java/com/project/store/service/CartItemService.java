package com.project.store.service;

import com.project.store.model.CartItem;
import com.project.store.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;


    public void createCartItem(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }


}
