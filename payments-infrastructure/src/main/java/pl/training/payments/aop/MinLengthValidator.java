package pl.training.payments.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.training.payments.application.annotations.MinLength;

@Aspect
@Component
public class MinLengthValidator {

    @Before("execution(* *(@pl.training.payments.application.annotations.MinLength (*)))")
    public void validate(JoinPoint joinPoint) throws Throwable {
        Annotations.taskForArgument(joinPoint, MinLength.class, (String argument, MinLength minLength) -> {
            if (argument.length() < minLength.value()) {
                throw new IllegalArgumentException("Value is too short, required length is: " + minLength.value());
            }
        });
    }

}
