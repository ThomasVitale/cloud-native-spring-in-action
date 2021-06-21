package com.polarbookshop.catalogservice.persistence;

import java.time.Year;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class YearAttributeConverter implements AttributeConverter<Year, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Year attribute) {
        return attribute.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Integer dbData) {
        return Year.of(dbData);
    }

}
