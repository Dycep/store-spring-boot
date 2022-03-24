package com.project.store.controller;


import com.project.store.controller.dto.PurchaseRequest;
import com.project.store.service.CartService;
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
    public void showCart(){

    }

    @PostMapping
    public void saveCart(@Valid @RequestBody PurchaseRequest purchaseRequest){
        cartService.saveCart(purchaseRequest);
    }

}
