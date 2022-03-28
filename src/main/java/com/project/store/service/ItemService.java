package com.project.store.service;

import com.project.store.exception.item.ItemAlreadyExistsException;
import com.project.store.exception.item.ItemNotFoundException;
import com.project.store.model.Item;
import com.project.store.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ItemService{

    private final ItemRepository itemRepository;


    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public Item getItemById(Long id){
        return itemRepository.findById(id).orElseThrow(()->new ItemNotFoundException("Item with id"+id+" was not found"));
    }

    public void updateItem(Long id, String name, String description, BigDecimal price){
        Item item = getItemById(id);

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

    public void createItem(Item item){
        if (itemRepository.existsItemByNameAndDescriptionAndPrice(item.getName(), item.getDescription(), item.getPrice())) {
            throw new ItemAlreadyExistsException("Item already exists");
        }
        itemRepository.save(item);
    }

    public void deleteItemById(Long id){
        if(!itemRepository.existsById(id)){
            throw new ItemNotFoundException("Item with id "+id+" does not exist");
        }
        itemRepository.deleteById(id);
    }
}
