package com.tcc.helpinghand.converters;

import com.tcc.helpinghand.enums.Difficulty;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DifficultyConverter implements AttributeConverter<Difficulty, String> {

    @Override
    public String convertToDatabaseColumn(Difficulty difficulty) {
        return difficulty.getLabel();
    }

    @Override
    public Difficulty convertToEntityAttribute(String dbData) {
        return Difficulty.getByName(dbData);
    }
}
