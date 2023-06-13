package pl.training.payments.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pl.training.payments.application.annotations.Retry;

@Aspect
@Component
@Log
public class Repeater {

    @Around("@annotation(retry)")
    public Object tryExecute(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        var attempt = 0;
        Throwable throwable;
        do {
            attempt++;
            try {
                return joinPoint.proceed();
            } catch (Throwable currentThrowable) {
                throwable = currentThrowable;
                log.info("Execution of method \"%s\" failed with exception: %s (attempt: %d)".formatted(joinPoint.getSignature(), throwable.getClass().getSimpleName(), attempt));
            }
        } while (attempt < retry.attempts());
        throw throwable;
    }

}
