package com.project.store.service;

import com.project.store.model.Cart;
import com.project.store.model.CartItem;
import com.project.store.model.Item;
import com.project.store.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartItemServiceUnitTest {

    @Mock
    private CartItemRepository cartItemRepository;

    private CartItemService underTest;

    @BeforeEach
    void setUp(){
        underTest = new CartItemService(cartItemRepository);
    }

    @Test
    void shouldCreateCartItem() {
        CartItem item = new CartItem(new Cart(), new Item());
        underTest.createCartItem(item);
        verify(cartItemRepository).save(item);
    }
}