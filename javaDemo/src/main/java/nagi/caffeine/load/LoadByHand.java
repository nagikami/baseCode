package nagi.caffeine.load;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * 手动加载
 */
public class LoadByHand {
    public static void main(String[] args) {
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(1000)
                .build();

        String key = "foo";
        // 查找一个缓存元素，没有查找到的时候返回null
        System.out.println(cache.getIfPresent(key));
        // 查找缓存，如果缓存不存在则生成缓存元素（手动加载），如果无法生成则返回null
        System.out.println(cache.get(key, k -> "bar"));
        // 添加或者更新一个缓存元素
        cache.put("a", "b");
        System.out.println(cache.get("a", k -> null));
        // 移除一个缓存元素
        cache.invalidate(key);
    }
}
