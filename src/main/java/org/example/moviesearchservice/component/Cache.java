package org.example.moviesearchservice.component;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class Cache {
    private final Map<String, Object> hashMap = new HashMap<>();

    public void addToCache(String key, Object value) {
        hashMap.put(key, value);
    }

    public Object getFromCache(String key) {
        return hashMap.get(key);
    }

    public void removeFromCache(String key) {
        hashMap.remove(key);
    }
}
