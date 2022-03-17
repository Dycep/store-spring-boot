package com.project.store.service;

import com.project.store.model.CartItem;
import com.project.store.repository.CartItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private ArrayList<Long> itemIdList;


    public void save(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }

    public void addItemToCart(HttpServletRequest request, Long itemId){
        itemIdList.add(itemId);
        request.getSession().setAttribute("cartItem", itemIdList);
        System.out.println(request.getSession().getAttribute("cartItem"));
    }
}
