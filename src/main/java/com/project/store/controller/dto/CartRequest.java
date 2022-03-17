package com.project.store.controller.dto;

import com.project.store.model.Item;
import com.project.store.model.User;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartRequest {

    @NotNull
    private List<Item> items;

    private final User user = (User) SecurityContextHolder
            .getContext().getAuthentication().getPrincipal();

    private String comment;


}
