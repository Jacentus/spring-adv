package pl.training.payments.aop;

import lombok.Setter;
import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransaction;
import pl.training.payments.domain.InsufficientFundsException;
import pl.training.payments.domain.Money;

import java.util.List;

//@Order(1_00)
@Aspect
@Component
@Log
public class PaymentServiceConsoleLogger implements Ordered {

    @Value("${loggerOrder}")
    @Setter
    private int loggerOrder;

    @Pointcut("bean(paymentService)")
    void process() {
    }

    @Before(value = "@annotation(pl.training.payments.application.annotations.Loggable) && args(number, money)"/*, argNames = "joinPoint,number,money"*/)
    public void beforeStart(JoinPoint joinPoint, CardNumber number, Money money) {
        // var args = joinPoint.getArgs();
        log.info("### New charge request for card: %s".formatted(number.toString()));
    }

    @AfterReturning(value = "execution(* pl.training.payments.*.servic*.PaymentService.getTransactio*(..))", returning = "transactions")
    // @AfterReturning(value = "execution(java.util.List pl.training.payments.application.services.PaymentService.getTransactions(pl.training.payments.domain.CardNumber))", returning = "transactions")
    // @AfterReturning(value = "process()", returning = "transactions")
    public void onSuccess(List<CardTransaction> transactions) {
        log.info("### Card transactions: %s".formatted(transactions));
    }

    @AfterThrowing(value = "process()", throwing = "exception")
    public void onFailure(InsufficientFundsException exception) {
        log.info("### Card transactions reject: %s".formatted(exception.getClass().getSimpleName()));
    }

    @After("process()")
    public void afterFinish() {
        log.info("### Card transaction finished");
    }

    @Around("process()")
    public Object chargeInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        // before
        try {
            var result = joinPoint.proceed();
            // afterReturning
            return result;
        } catch (Throwable throwable) {
            // afterThrowing
            throw throwable;
        } finally {
            // after
        }
    }

    @Override
    public int getOrder() {
        return loggerOrder;
    }

}
