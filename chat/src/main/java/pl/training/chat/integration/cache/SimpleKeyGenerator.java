package pl.training.chat.integration.cache;

import lombok.extern.java.Log;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Log
public class SimpleKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        var key = method.getName() + Arrays.stream(params)
                .map(Object::toString)
                .toList();
        log.info("Generating new cache key: " + key);
        return key;
    }

}
