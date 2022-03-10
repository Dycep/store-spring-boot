package com.project.store.controller;


import com.project.store.controller.dto.CartRequest;
import com.project.store.model.User;
import com.project.store.service.CartService;
import com.project.store.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public void sendCart(@Valid @RequestBody CartRequest cartRequest){
        cartService.saveCart(cartRequest);

    }

}
