package pl.training.payments.commons.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class Annotations {

    public static  <T extends Annotation> T getClassAnnotation(ProceedingJoinPoint joinPoint, Class<T> type) {
        return joinPoint.getTarget().getClass().getAnnotation(type);
    }

    public static  <T extends Annotation> T getMethodAnnotation(ProceedingJoinPoint joinPoint, Class<T> type) throws NoSuchMethodException {
        return getTargetMethod(joinPoint).getAnnotation(type);
    }

    public static  <T extends Annotation> T findAnnotation(ProceedingJoinPoint joinPoint, Class<T> type) throws NoSuchMethodException {
        var annotation = getClassAnnotation(joinPoint, type);
        return annotation != null ? annotation : getMethodAnnotation(joinPoint, type);
    }

    public static Method getTargetMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        var signature = (MethodSignature) joinPoint.getSignature();
        var methodName = signature.getMethod().getName();
        var parameterTypes = signature.getMethod().getParameterTypes();
        return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
    }

    public static <T extends Annotation> Optional<T> findAnnotation(Annotation[] annotations, Class<T> type) {
        return Arrays.stream(annotations)
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst();
    }

}
