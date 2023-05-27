package it.municipalitiesregistry.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StringTrimmerConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute != null ? attribute.trim() : null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }

}
