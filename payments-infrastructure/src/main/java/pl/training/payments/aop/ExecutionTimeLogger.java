package pl.training.payments.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.training.payments.application.annotations.ExecutionTime;

import static pl.training.payments.application.annotations.ExecutionTime.TimeUnit.NS;

@Order(1_000)
@Aspect
@Component
@Log
public class ExecutionTimeLogger {

    @Around("@annotation(executionTime)")
    public Object log(ProceedingJoinPoint proceedingJoinPoint, ExecutionTime executionTime) throws Throwable {
        var timeUnit = executionTime.timeUnit();
        var startTime = getTime(timeUnit);
        var result = proceedingJoinPoint.proceed();
        var endTime = getTime(timeUnit);
        var totalTime = endTime - startTime;
        log.info("Method %s executed in %d %s".formatted(proceedingJoinPoint.getSignature(), totalTime, timeUnit.name().toLowerCase()));
        return result;
    }


    private long getTime(ExecutionTime.TimeUnit timeUnit) {
        return timeUnit == NS ?System.nanoTime() : System.currentTimeMillis();
    }

}
