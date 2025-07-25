package com.example.springdeploytest.redis_cache;

import java.time.Duration;

public interface RedisCacheService {
    <K, V> void setKeyAndValue(K key, V value);
    <K, V> void setKeyAndValue(K key, V value, Duration timeToLive);
    <T> T getValueByKey(String key, Class<T> clazz);
    void deleteByKey(String token);
}
