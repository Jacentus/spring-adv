package pl.training.payments.commons.data.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MoneyValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Money {

    String message() default "is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    double minValue() default 1;

}
