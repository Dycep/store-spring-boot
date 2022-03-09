package com.project.store.controller;

import com.project.store.model.Item;
import com.project.store.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/item")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping()
    public List<Item> showAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item showItem(@PathVariable Long id){
        return itemService.getItem(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String description, @RequestParam(required = false) BigDecimal price){
        itemService.updateItem(id, name, description, price);
    }

    @PostMapping("/save")
    public void saveItem(@RequestBody Item item, BindingResult bindingResult){
        if (!bindingResult.hasErrors())
            itemService.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id){
        itemService.deleteItemById(id);
    }
}
