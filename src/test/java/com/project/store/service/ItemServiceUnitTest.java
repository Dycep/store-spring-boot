package com.project.store.service;

import com.project.store.exception.item.ItemAlreadyExistsException;
import com.project.store.exception.item.ItemNotFoundException;
import com.project.store.model.Item;
import com.project.store.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceUnitTest {
    @Mock
    private ItemRepository itemRepository;
    private ItemService underTest;
    @BeforeEach
    void setUp() {
        underTest = new ItemService(itemRepository);
    }
    @Test
    void shouldGetAllItems() {
        underTest.getAllItems();
        verify(itemRepository).findAll();
    }
    @Test
    void shouldCreateItem() {
        Item item = new Item("name", "description", valueOf(10));
        underTest.createItem(item);
        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemArgumentCaptor.capture());
        Item capturedItem = itemArgumentCaptor.getValue();
        assertThat(capturedItem).isEqualTo(item);
    }

    @Test
    void willThrowItemAlreadyExistsException(){
        String name = "name";
        String description = "description";
        BigDecimal price = valueOf(10);
        Item item = new Item(name, description, price);

        given(itemRepository.existsItemByNameAndDescriptionAndPrice(name, description, price))
                .willReturn(true);
        assertThatThrownBy(() -> underTest.createItem(item))
                .isInstanceOf(ItemAlreadyExistsException.class)
                .hasMessageContaining("Item already exists");
        verify(itemRepository, never()).save(any());
    }

    @Test
    void shouldGetItemById() {
        Item item = new Item();
        item.setId(89L);

        given(itemRepository.findById(item.getId())).willReturn(Optional.of(item));
        underTest.getItemById(89L);
        verify(itemRepository).findById(item.getId());
    }

    @Test
    void shouldUpdateItem() {
        Item item = new Item(89L, "name", "description", valueOf(10));

        given(itemRepository.findById(item.getId())).willReturn(Optional.of(item));
        underTest.updateItem(89L, "nam","des");
        verify(itemRepository).updateItemName(89L, "nam");
        verify(itemRepository).updateItemDescription(89L, "des");
        assertEquals(item.getPrice(), valueOf(10));
    }

    @Test
    void shouldNotUpdateItem(){
        Item item = new Item(89L, "n", "d", valueOf(10));
        given(itemRepository.findById(item.getId())).willReturn(Optional.of(item));

        underTest.updateItem(89L, "n", "d");
        verify(itemRepository, never()).updateItemName(89L, "n");
        verify(itemRepository, never()).updateItemDescription(89L, "d");
        assertEquals(item.getPrice(), valueOf(10));
    }

    @Test
    void shouldDeleteItemById() {
        long id = 10;
        given(itemRepository.existsById(id))
                .willReturn(true);
        underTest.deleteItemById(id);
        verify(itemRepository).deleteById(id);
    }

    @Test
    void willThrowItemNotFoundException(){
        long id = 10;
        given(itemRepository.existsById(id))
                .willReturn(false);
        assertThatThrownBy(() -> underTest.deleteItemById(id))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessageContaining("Item with id "+id+" does not exist");
        verify(itemRepository, never()).deleteById(any());
    }
}