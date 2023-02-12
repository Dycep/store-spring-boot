package com.project.store.repository;

import com.project.store.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findItemByNameAndDescriptionAndPrice(String name, String description, BigDecimal price);

    boolean existsItemByNameAndDescriptionAndPrice(String name, String description, BigDecimal price);

    @Transactional
    @Modifying
    @Query("UPDATE Item i " +
            "SET i.name = ?2 WHERE i.id = ?1")
    void updateItemName(Long id,String name);

    @Transactional
    @Modifying
    @Query("UPDATE Item i " +
            "SET i.description = ?2 WHERE i.id = ?1")
    void updateItemDescription(Long id, String description);
}
