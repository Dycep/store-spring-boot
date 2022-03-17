package com.project.store.controller;


import com.project.store.controller.dto.CartRequest;
import com.project.store.model.User;
import com.project.store.service.CartService;
import com.project.store.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public void showCart(HttpServletRequest request){
        cartService.createCart(request);
    }

    @PostMapping
    public void saveCart(@Valid @ModelAttribute CartRequest cartRequest){

    }

}
