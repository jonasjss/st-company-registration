package com.superterminais.companyregistration.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanSNConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return Boolean.TRUE.equals(attribute) ? "S" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "S".equalsIgnoreCase(dbData);
    }
}