package com.booking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GroceryItemDto {
    private String name;
    private double price;
    private int quantity;
}
