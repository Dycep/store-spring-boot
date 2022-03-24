package com.project.store.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "cart_item")
@Entity
@Data
public class CartItem {

    @Id
    @SequenceGenerator(
            name = "cart_item_sequence",
            sequenceName = "cart_item_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "cart_item_sequence"
    )
    @Column(name = "id")
    private Integer id;

    //create cart with
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public CartItem(Cart cart, Item item) {
        this.cart = cart;
        this.item = item;
    }
}
