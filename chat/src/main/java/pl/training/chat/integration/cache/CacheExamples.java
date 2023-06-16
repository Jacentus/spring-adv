package pl.training.chat.integration.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Log
@RequiredArgsConstructor
public class CacheExamples implements ApplicationRunner {

    private final Calculator calculator;
    private final CacheManager cacheManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("First attempt: " + calculator.add(1, 1));
        log.info("Second attempt: " + calculator.add(1, 1));
        calculator.clearCache();
        log.info("Third attempt: " + calculator.add(1, 1));
        calculator.clearCacheEntry("add[1, 1]");
        calculator.addCacheEntry("add[1, 1]", 2);
        log.info("Fourth attempt: " + calculator.add(1, 1));

        Optional.ofNullable(cacheManager.getCache("calculation"))
                .ifPresent(Cache::clear);
    }

}
