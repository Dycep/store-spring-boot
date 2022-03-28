package com.project.store.repository;

import com.project.store.model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryUnitTest {

    @Autowired
    private ItemRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void existsItemByNameAndDescriptionAndPrice() {
        String name = "name";
        String description = "description";
        BigDecimal price = BigDecimal.valueOf(10);

        Item item = new Item(name, description, price);
        underTest.save(item);

        boolean expected = underTest.existsItemByNameAndDescriptionAndPrice(name, description, price);
        assertThat(expected).isTrue();
    }

    @Test
    void shouldCheckWhenItemDoesntExist(){
        String name = "name";
        String description = "description";
        BigDecimal price = BigDecimal.valueOf(10);

        boolean expected = underTest.existsItemByNameAndDescriptionAndPrice(name, description, price);
        assertThat(expected).isFalse();

    }
}