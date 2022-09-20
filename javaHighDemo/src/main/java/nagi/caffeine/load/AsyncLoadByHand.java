package nagi.caffeine.load;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AsyncLoadByHand {
    public static void main(String[] args) throws Exception {
        AsyncCache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .maximumSize(1000)
                .buildAsync();

        String key = "foo";
        // 查找缓存元素，如果不存在，则异步生成
        CompletableFuture<String> future = cache.get(key, k -> "bar");
        System.out.println(future.get());
        // 添加或者更新一个缓存元素，异步计算value
        cache.put(key, CompletableFuture.supplyAsync(
                () -> "foo"
        ));
        // 移除一个缓存元素，synchronous()方法保证阻塞直到异步缓存生成完毕
        cache.synchronous().invalidate(key);
    }
}
