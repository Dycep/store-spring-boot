package com.project.store.service;

import com.project.store.exception.ItemAlreadyExistsException;
import com.project.store.exception.ItemNotFoundException;
import com.project.store.model.Item;
import com.project.store.repository.ItemRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    private ItemService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ItemService(itemRepository);
    }


    @Test
    void canGetAllItems() {
        //when
        underTest.getAllItems();
        //then
        verify(itemRepository).findAll();
    }

    @Test
    void canCreateItem() {
        Item item = new Item(
                "name",
                "description",
                BigDecimal.valueOf(10)
        );
        underTest.createItem(item);

        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemArgumentCaptor.capture());

        Item capturedItem = itemArgumentCaptor.getValue();
        assertThat(capturedItem).isEqualTo(item);
    }

    @Test
    void willThrownItemAlreadyExistsException(){
        String name = "name";
        String description = "description";
        BigDecimal price = BigDecimal.valueOf(10);

        Item item = new Item(name, description, price);

        given(itemRepository.existsItemByNameAndDescriptionAndPrice(name, description, price))
                .willReturn(true);

        assertThatThrownBy(() -> underTest.createItem(item))
                .isInstanceOf(ItemAlreadyExistsException.class)
                .hasMessageContaining("Item already exists");

        verify(itemRepository, never()).save(any());
    }

    @Test
    void getItem() {
    }

    @Test
    void updateItem() {
    }

    @Test
    void canDeleteItemById() {
        long id = 10;
        given(itemRepository.existsById(id))
                .willReturn(true);

        underTest.deleteItemById(id);

        verify(itemRepository).deleteById(id);
    }

    @Test
    void willThrownItemNotFoundException(){
        long id = 10;
        given(itemRepository.existsById(id))
                .willReturn(false);

        assertThatThrownBy(() -> underTest.deleteItemById(id))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessageContaining("Item with id "+id+" does not exist");

        verify(itemRepository, never()).deleteById(any());
    }
}