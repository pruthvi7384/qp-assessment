package com.booking.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ESBExcepcionMessage extends Exception {
    private static final long serialVersionUID = 1L;
    private final ESBExcepcionType faultInfo;
}