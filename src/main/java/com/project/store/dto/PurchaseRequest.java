package com.project.store.dto;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class PurchaseRequest {

    private final ArrayList<Long> itemIdList;
    private final String email;
    private final String phone;
    private final String comment;

}

