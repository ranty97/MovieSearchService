package component;


import org.example.moviesearchservice.component.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CacheTest {
    private Cache cache;

    public static final int MAX_CACHE_SIZE = 10;


    @BeforeEach
    void setUp() {
        cache = new Cache();
    }

    @Test
    void testRemoveEldestEntryRemovesEldestEntry() {
        Map<String, Object> map = new LinkedHashMap<>(MAX_CACHE_SIZE + 1, 1, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
        for (int i = 0; i < MAX_CACHE_SIZE + 1; i++) {
            map.put("key" + i, "value" + i);
        }
        assertEquals(MAX_CACHE_SIZE + 1, map.size());
        map.put("newKey", "newValue");
        assertEquals(MAX_CACHE_SIZE, map.size());
        assertNull(map.get("key0"));
    }

    @Test
    void testPutAndGet() {
        String key = "key";
        String value = "value";

        cache.put(key, value);
        Object retrievedValue = cache.get(key);

        assertEquals(value, retrievedValue);
    }
}
