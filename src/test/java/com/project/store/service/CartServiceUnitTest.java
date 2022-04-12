package com.project.store.service;

import com.project.store.dto.PurchaseRequest;
import com.project.store.model.Cart;
import com.project.store.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartServiceUnitTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserService userService;
    @Mock
    private ItemService itemService;
    @Mock
    private CartItemService cartItemService;

    private CartService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CartService(
                cartRepository,userService,itemService,cartItemService);
    }

    @Test
    void shouldSaveCart() {
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(1L);
        PurchaseRequest request = new PurchaseRequest(
                ids,
                "email@mail.ru",
                "373773737333",
                ""
        );
        underTest.saveCart(request);
        verify(userService).getUserByEmail(request.getEmail());
        verify(cartRepository).save(any(Cart.class));
        verify(itemService).getItemById(anyLong());
    }
}