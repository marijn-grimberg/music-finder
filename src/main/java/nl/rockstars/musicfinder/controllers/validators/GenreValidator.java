package nl.rockstars.musicfinder.controllers.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenreValidator implements ConstraintValidator<Genre, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.contains("Metal");
    }
}
