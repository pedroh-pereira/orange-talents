package br.com.zup.orange.talents.converters;

import java.util.Objects;
import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.zup.orange.talents.enums.EnumState;

@Converter(autoApply = true)
public class EnumStateConverter implements AttributeConverter<EnumState, String> {

  @Override
  public String convertToDatabaseColumn(EnumState attribute) {
    return Objects.isNull(attribute) ? null : attribute.name();
  }

  @Override
  public EnumState convertToEntityAttribute(String dbData) {
    if(Objects.isNull(dbData)) return null;
    return Stream.of(EnumState.values())
                .filter(item -> item.name().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
  }
  
}
