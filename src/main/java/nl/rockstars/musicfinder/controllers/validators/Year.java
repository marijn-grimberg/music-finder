package nl.rockstars.musicfinder.controllers.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = YearValidator.class)
@Documented
public @interface Year {
    String message() default "Year has to be before 2016";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
