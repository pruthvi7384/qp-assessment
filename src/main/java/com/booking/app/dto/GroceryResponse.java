package com.booking.app.dto;

import com.booking.app.exceptions.ESBExcepcionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroceryResponse extends ESBExcepcionType {
    Object response;
}
