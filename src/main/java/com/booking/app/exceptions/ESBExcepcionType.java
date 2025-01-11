package com.booking.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ESBExcepcionType implements Serializable {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorDescription;
}