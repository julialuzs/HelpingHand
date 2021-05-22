package com.tcc.helpinghand.converters;

import com.tcc.helpinghand.enums.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status.getLabel();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return Status.getByName(dbData);
    }
}
