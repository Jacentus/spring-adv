package pl.training.payments.commons.data.validation;

import org.javamoney.moneta.FastMoney;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MoneyValidator implements ConstraintValidator<Money, String> {

    private double minValue;

    @Override
    public void initialize(Money constraintAnnotation) {
        minValue = constraintAnnotation.minValue();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result;
        try {
            result = FastMoney.parse(value).isGreaterThanOrEqualTo(minValue);
        } catch (Exception exception) {
            result = false;
        }
        return result;
    }

}
