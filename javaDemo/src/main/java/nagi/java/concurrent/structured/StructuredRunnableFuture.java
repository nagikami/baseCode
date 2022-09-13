package nagi.java.concurrent.structured;

import java.util.List;
import java.util.concurrent.RunnableFuture;

public interface StructuredRunnableFuture<V> extends RunnableFuture<V> {
    void attach(List<StructuredRunnableFuture<?>> futures);
}
