package nagi.caffeine.load;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * 自动加载
 */
public class LoadByAuto {
    public static void main(String[] args) {
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(1000)
                // 指定自动加载逻辑
                .build(k -> "bar");

        String key = "foo";
        // 查找缓存，如果缓存不存在则生成缓存元素（自动加载），如果无法生成则返回null
        System.out.println(cache.get(key));
    }
}
