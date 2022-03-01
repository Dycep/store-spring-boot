package com.project.store.service;

import com.project.store.model.Item;
import com.project.store.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemService{

    private final ItemRepository itemRepository;


    public List<Item> getAllItems(){
        System.out.println(itemRepository.findAll());
        return itemRepository.findAll();
    }

    public Item getItem(Long id){
        System.out.println(itemRepository.getById(id));
        return itemRepository.getById(id);
    }

    public void save(Item item){
        itemRepository.save(item);
    }

    public void deleteItemById(Long id){
        itemRepository.deleteById(id);
    }
}
