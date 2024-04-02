package org.example.moviesearchservice.component;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Data
public class Cache {

    public static final int MAX_CACHE_SIZE = 10;
    private final Map<String, Object> linkedHashMap = new LinkedHashMap<>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
            return size() > MAX_CACHE_SIZE;
        }
    };

    public void put(String key, Object value) {
        linkedHashMap.put(key, value);
    }

    public Object get(String key) {
        return linkedHashMap.get(key);
    }

    public void removeNote(String key) {
        linkedHashMap.remove(key);
    }
}
