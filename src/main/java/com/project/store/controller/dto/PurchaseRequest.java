package com.project.store.controller.dto;

import com.project.store.model.User;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class PurchaseRequest {

    private final ArrayList<Long> itemIdList;
    private final String email;
    private final String phone;
    private final String comment;
}
