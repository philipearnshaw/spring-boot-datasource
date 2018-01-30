package springbootdatasource.validators;

import java.util.Optional;

import springbootdatasource.exception.NotFoundException;

public class EntityValidator {
    
    public static <T> Optional<T> requireNonNull(final Optional<T> optional, final String entityId) {
        if (optional == null || optional.isPresent() == false) {
            throw new NotFoundException("Entity id: " + entityId);
        }
        return optional;
    } 
}
