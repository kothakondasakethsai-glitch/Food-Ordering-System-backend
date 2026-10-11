package com.foodordering.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {
    @NotNull
    private Long foodItemId;

    @NotNull
    private Integer quantity;
}
