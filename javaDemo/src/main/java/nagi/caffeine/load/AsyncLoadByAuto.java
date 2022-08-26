package nagi.caffeine.load;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 异步自动
 */
public class AsyncLoadByAuto {
    public static void main(String[] args) throws Exception {
        AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(1000)
                // 异步的封装一段同步操作来生成缓存元素
                .buildAsync(k -> "bar");

        String key = "foo";
        // 查找缓存元素，如果其不存在，将会异步进行生成
        CompletableFuture<String> future = cache.get(key);
        System.out.println(future.get());
    }
}
