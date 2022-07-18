package tech.zdrzalik.courses.model.AccessLevel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class AccessLevelConverter implements AttributeConverter<AccessLevel, String> {

    @Override
    public String convertToDatabaseColumn(AccessLevel accessLevel) {
        if (accessLevel == null) {
            return null;
        }
        return accessLevel.getLevel();
    }

    @Override
    public AccessLevel convertToEntityAttribute(String s) {
        if (s == null) {
            return  null;
        }
        return Stream.of(AccessLevel.values())
                .filter(a -> a.getLevel().equals(s))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
