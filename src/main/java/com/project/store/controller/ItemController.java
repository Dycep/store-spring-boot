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


@RestController
@RequestMapping(path = "/")
@CrossOrigin("http:/localhost:3000")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping()
    public List<Item> showAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item showItem(@PathVariable Long id){
        return itemService.getItemById(id);
    }


    @PutMapping("/{id}")
    public void updateItem(@PathVariable Long id,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String description){
        itemService.updateItem(id, name, description);
    }

    @PostMapping("/create")
    public void createItem(@Valid @RequestBody Item item){
        itemService.createItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id){
        itemService.deleteItemById(id);
    }
}
