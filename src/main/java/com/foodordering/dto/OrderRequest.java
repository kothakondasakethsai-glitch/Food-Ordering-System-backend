package com.foodordering.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank
    private String deliveryAddress;

    private String deliveryPhone;
    private String specialInstructions;
}
