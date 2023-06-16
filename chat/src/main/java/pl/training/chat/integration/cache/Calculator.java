package pl.training.chat.integration.cache;

import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Log
@CacheConfig(cacheNames = "calculations")
@Service
public class Calculator {

    @Cacheable(/*cacheNames = "calculations", condition = "#a < #b",*/ keyGenerator = "simpleKeyGenerator")
    public int add(int a, int b) {
        log.info("Calculating sum of %s and %s".formatted(a, b));
        return a + b;
    }

    @CacheEvict(/*cacheNames = "calculations",*/ allEntries = true)
    public void clearCache() {
        log.info("Cache is empty");
    }

    @CacheEvict(/*cacheNames = "calculations",*/ key = "#key", condition = "true")
    public void clearCacheEntry(String key) {
        log.info("Removing entry with key " + key);
    }

    @CachePut(/*cacheNames = "calculations",*/ key = "#key")
    public int addCacheEntry(String key, int value) {
        return value;
    }

}
