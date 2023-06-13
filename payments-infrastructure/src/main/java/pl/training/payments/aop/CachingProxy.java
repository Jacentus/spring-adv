package pl.training.payments.aop;

import lombok.extern.java.Log;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Log
public class CachingProxy implements MethodInterceptor {

    private final Map<String, Object> cache = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        var key = calculateKey(invocation);
        if (cache.containsKey(key)) {
            log.info("Reading from cache");
            return cache.get(key);
        }
        var result = invocation.proceed();
        cache.put(key, result);
        return result;
    }

    private String calculateKey(MethodInvocation invocation) {
        return invocation.getMethod().getName() + Arrays.stream(invocation.getArguments())
                .map(Object::hashCode)
                .map(Object::toString)
                .collect(joining());
    }

}
