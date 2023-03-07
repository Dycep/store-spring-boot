package com.project.store.controller;

import com.project.store.model.Item;
import com.project.store.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping(path = "/")
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping()
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id){
        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public void updateItem(@PathVariable Long id,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String description){
        itemService.updateItem(id, name, description);
    }

    @PostMapping("/create")
    public Item createItem(@Valid @RequestBody Item item){
        return itemService.createItem(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id){
        itemService.deleteItemById(id);
    }
}
