package com.project.store.service;

import com.project.store.exception.ItemNotFoundException;
import com.project.store.model.Item;
import com.project.store.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService{

    private final ItemRepository itemRepository;


    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public Item getItem(Long id){
        return itemRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Item by id"+id+" was not found"));
    }

    @Transactional
    public void updateItem(Long id, Item updatedItem){
        Item item = itemRepository.findById(id).orElseThrow(()-> new IllegalStateException("Item with id" + id + " does not exist"));

        if(updatedItem.getName()!= null && updatedItem.getName().length()>0 && !Objects.equals(item.getName(), updatedItem.getName())){
            item.setName(updatedItem.getName());
        }

        if(updatedItem.getDescription()!=null && updatedItem.getDescription().length() > 0 && !Objects.equals(item.getDescription(), updatedItem.getDescription())){
            item.setDescription(updatedItem.getDescription());
        }

        if (updatedItem.getPrice()!=null && updatedItem.getPrice().compareTo(item.getPrice())!=0 && !Objects.equals(item.getPrice(), updatedItem.getPrice())){
            item.setPrice(updatedItem.getPrice());
        }
    }

    public void save(Item item){
        itemRepository.save(item);
    }

    public void deleteItemById(Long id){
        if(!itemRepository.existsById(id)){
            throw new ItemNotFoundException("Item with id "+id+" does not exist");
        }
        itemRepository.deleteById(id);
    }
}
