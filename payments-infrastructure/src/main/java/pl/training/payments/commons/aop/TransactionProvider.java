package pl.training.payments.commons.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import pl.training.payments.commons.Atomic;

import static pl.training.payments.commons.aop.Annotations.findAnnotation;

@Aspect
@Component
@RequiredArgsConstructor
public class TransactionProvider {

    private final PlatformTransactionManager transactionManager;

    @Around("@annotation(pl.training.payments.commons.Atomic) || within(@pl.training.payments.commons.Atomic *)")
    public Object runWithTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        var atomic = Annotations.findAnnotation(joinPoint, Atomic.class);
        var transactionDefinition = getTransactionDefinition(atomic);
        var transaction = transactionManager.getTransaction(transactionDefinition);
        try {
            var result = joinPoint.proceed();
            transactionManager.commit(transaction);
            return result;
        } catch (Throwable throwable) {
            transaction.setRollbackOnly();
            throw throwable;
        }
    }

    private DefaultTransactionDefinition getTransactionDefinition(Atomic atomic) {
        var transactionDefinition = new DefaultTransactionDefinition();
        var timout = atomic.timeout();
        if (timout != 0) {
            transactionDefinition.setTimeout(timout);
        }
        return transactionDefinition;
    }

}
