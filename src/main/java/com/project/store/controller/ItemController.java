package com.project.store.controller;

import com.project.store.model.Item;
import com.project.store.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/item")
@AllArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @GetMapping()
    public String showAllItems(@ModelAttribute("items") Item items){
        itemService.getAllItems();
        return "index";
    }

    @GetMapping("/{id}")
    public String showItem(@PathVariable Long id, @ModelAttribute("item") Item item){
        itemService.getItem(id);
        return "show_item";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute("item") Item item){
        itemService.getItem(id);
        return "edit_item";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("item") Item item, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "edit_item";
        itemService.save(item);
        return "redirect:/index";
    }

    @GetMapping("/item_form")
    public String itemForm(@ModelAttribute("item") Item item){
        return "item_form";
    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute("item") Item item, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "item_form";

        itemService.save(item);
        return "redirect:/item";
    }


    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable Long id){
        itemService.deleteItemById(id);
        return "redirect:/item";
    }
}
