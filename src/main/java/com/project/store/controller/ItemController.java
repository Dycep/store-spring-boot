package com.project.store.controller;

import com.project.store.model.Cart;
import com.project.store.model.Item;
import com.project.store.service.CartItemService;
import com.project.store.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(path = "/item")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CartItemService cartItemService;

    @GetMapping()
    public String showAllItems(Model model, HttpServletRequest request){
        model.addAttribute("items", itemService.getAllItems());
        System.out.println(request.getSession().getId());
        return "items";
    }

    @GetMapping("show/{id}")
    public String showItem(@PathVariable Long id, Model model){
        model.addAttribute("item",itemService.getItem(id));
        return "show_item";
    }


//    @PutMapping("/{id}")
//    public void updateItem(@PathVariable Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String description, @RequestParam(required = false) BigDecimal price){
//        itemService.updateItem(id, name, description, price);
//    }


    @PutMapping("/{id}")
    public String updateItem(@ModelAttribute("person") @Valid Item item, @PathVariable Long id){
        itemService.updateItem(id, item);
        return "redirect:/item";
    }

    @GetMapping("/create")
    public String createItem(@ModelAttribute("item") Item item){
        return "item_form";
    }


    @PutMapping("add/{id}")
    public String addItemToCart(@PathVariable Long id, HttpServletRequest request){
        cartItemService.addItemToCart(request, id);
        return "redirect:/item";
    }

    @PostMapping("/save")
    public String saveItem(@Valid @ModelAttribute("item") Item item){
        itemService.save(item);
        return "redirect:/item";
    }

    @DeleteMapping("delete/{id}")
    public String deleteItem(@PathVariable Long id){
        itemService.deleteItemById(id);
        return "redirect:/item";
    }
}
