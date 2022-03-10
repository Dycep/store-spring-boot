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

    //TODO custom exception
    public Item getItem(Long id){
        return itemRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Item by id"+id+" was not found"));
    }

    @Transactional
    public void updateItem(Long id, String name, String description, BigDecimal price){
        Item item = itemRepository.findById(id).orElseThrow(()-> new IllegalStateException("Item with id" + id + " does not exist"));

        if(name!= null && name.length()>0 && !Objects.equals(item.getName(), name)){
            item.setName(name);
        }

        if(description!=null && description.length() > 0 && !Objects.equals(item.getDescription(), description)){
            item.setDescription(description);
        }

        if (price!=null && price.compareTo(item.getPrice())!=0 && !Objects.equals(item.getPrice(), price)){
            item.setPrice(price);
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
