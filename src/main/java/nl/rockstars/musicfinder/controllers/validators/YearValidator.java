package nl.rockstars.musicfinder.controllers.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<Year, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value < 2016;
    }
}
