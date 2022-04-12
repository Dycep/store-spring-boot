package com.project.store.service;

import com.project.store.dto.PurchaseRequest;
import com.project.store.model.Cart;
import com.project.store.model.CartItem;
import com.project.store.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final CartItemService cartItemService;

    public void saveCart(PurchaseRequest request){
        List<Long> itemIdList = request.getItemIdList();
        Cart cart = new Cart();
        cart.setUser(userService.getUserByEmail(request.getEmail()));
        cart.setComment(request.getComment());
        cartRepository.save(cart);

        for (Long id : itemIdList) {
            cartItemService.createCartItem(new CartItem(
                    cart,
                    itemService.getItemById(id))
            );
        }
    }
}
