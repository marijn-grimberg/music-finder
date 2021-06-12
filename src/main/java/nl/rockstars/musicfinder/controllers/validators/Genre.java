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
@Constraint(validatedBy = GenreValidator.class)
@Documented
public @interface Genre {
    String message() default "Genre has to contain 'Metal'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
