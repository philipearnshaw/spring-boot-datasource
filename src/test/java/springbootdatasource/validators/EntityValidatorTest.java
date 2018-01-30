package springbootdatasource.validators;

import static org.junit.Assert.assertTrue;
import static springbootdatasource.validators.EntityValidator.requireNonNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import springbootdatasource.exception.NotFoundException;
import springbootdatasource.model.Competition;

public class EntityValidatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = NotFoundException.class)
    public void testRequireNonNull_ShouldThrowExceptionForNullOptional() {
        
        final Optional<Competition> optional = null;
        requireNonNull(optional, "5");
    }

    @Test(expected = NotFoundException.class)
    public void testRequireNonNull_ShouldThrowExceptionForEmptyOptional() {
        
        final Optional<Competition> optional = Optional.empty();
        requireNonNull(optional, "5");
    }

    @Test
    public void testRequireNonNull_ShouldReturnSameOptional() {
        
        final Optional<Competition> expectedOptional = Optional.of(new Competition());
        final Optional<Competition> returnedOptional = requireNonNull(expectedOptional, "5");
        
        assertTrue(expectedOptional == returnedOptional);
    }
}
