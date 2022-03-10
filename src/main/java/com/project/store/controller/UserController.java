package com.project.store.controller;

import com.project.store.model.User;
import com.project.store.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/index")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


//    @GetMapping("/index")
//    public String index(){
//        return "index";
//    }
//
//    @GetMapping("/user_form")
//    public String showNewForm(@ModelAttribute("user") User user){
//        return "user_form";
//    }
//
//    @PostMapping("/new")
//    public String registerNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
//        if (bindingResult.hasErrors())
//            return "user_form";
//        userService.registerNewUser(user);
//        return "redirect:/view/index";
//    }
}
