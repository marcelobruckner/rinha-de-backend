package com.api.rinhadebackend.models.converters;

import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
  private static final String SPLIT_CHAR = ";";

  @Override
  public String convertToDatabaseColumn(List<String> stringList) {
    return stringList != null ? String.join(SPLIT_CHAR, stringList) : null;
  }

  @Override
  public List<String> convertToEntityAttribute(String string) {
    return string != null ? asList(string.split(SPLIT_CHAR)) : emptyList();
  }
}
