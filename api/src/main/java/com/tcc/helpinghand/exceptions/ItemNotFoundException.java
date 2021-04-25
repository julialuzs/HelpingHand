package com.tcc.helpinghand.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String item) {
        super("Exception: " + item + " não encontrado");
    }
}

