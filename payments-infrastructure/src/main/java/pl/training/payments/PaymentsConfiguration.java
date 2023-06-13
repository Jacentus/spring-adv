package pl.training.payments;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.Advisor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.ExpressionPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.training.payments.aop.CachingProxy;
import pl.training.payments.application.input.commands.ChargeCardCommandHandler;
import pl.training.payments.application.input.commands.ChargeCardFeesCommandHandler;
import pl.training.payments.application.input.queries.GetCardTransactionsQueryHandler;
import pl.training.payments.application.output.events.CardEventsPublisher;
import pl.training.payments.application.output.time.TimeProvider;
import pl.training.payments.application.services.PaymentService;
import pl.training.payments.domain.CardRepository;

import java.lang.reflect.Method;

@EnableJpaRepositories//(repositoryImplementationPostfix = "Custom")
@EnableTransactionManagement//(order = Integer.MIN_VALUE)
@EnableAspectJAutoProxy
@Configuration
public class PaymentsConfiguration {

    @Bean
    public PaymentService paymentService(CardRepository cardRepository, CardEventsPublisher cardEventsPublisher, TimeProvider timeProvider) {
        return new PaymentService(cardRepository, cardEventsPublisher, timeProvider);
    }

    @Bean
    public ChargeCardCommandHandler chargeCardCommandHandler(PaymentService paymentService) {
        return new ChargeCardCommandHandler(paymentService);
    }

    @Bean
    public ChargeCardFeesCommandHandler chargeCardFeesCommandHandler(PaymentService paymentService) {
        return new ChargeCardFeesCommandHandler(paymentService);
    }

    @Bean
    public GetCardTransactionsQueryHandler getTransactionsQueryHandler(PaymentService paymentService) {
        return new GetCardTransactionsQueryHandler(paymentService);
    }

    @Bean
    public Advisor cacheAdvisor() {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("bean(paymentService)");
        return new DefaultPointcutAdvisor(new CustomExpressionPointcut(), new CachingProxy());
    }

    class CustomExpressionPointcut implements ExpressionPointcut {

        @Override
        public String getExpression() {
            return "";
        }

        @Override
        public ClassFilter getClassFilter() {
            return clazz -> clazz.getSimpleName().equals(PaymentService.class.getSimpleName());
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MethodMatcher() {
                @Override
                public boolean matches(Method method, Class<?> targetClass) {
                    return true;
                }

                @Override
                public boolean isRuntime() {
                    return true;
                }

                @Override
                public boolean matches(Method method, Class<?> targetClass, Object... args) {
                    return true;
                }
            };
        }

    }

}
