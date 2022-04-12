package com.project.store.controller;

import com.project.store.dto.PurchaseRequest;
import com.project.store.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.store.model.UserRole.CUSTOMER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    void shouldSaveCart() throws Exception {
        mockMvc.perform(
                post("/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"itemIdList\": [1,2], " +
                        "\"email\":\"email@mail.ru\"}, " +
                        "\"phone\": \"375292999292\", " +
                        "\"comment\": \"comment\" }")
                .with(user("customer").roles(CUSTOMER.name())))
                .andExpect(status().isOk());

        verify(cartService).saveCart(any(PurchaseRequest.class));
    }
}