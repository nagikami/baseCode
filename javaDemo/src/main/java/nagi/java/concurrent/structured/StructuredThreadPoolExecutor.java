package nagi.java.concurrent.structured;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class StructuredThreadPoolExecutor extends ThreadPoolExecutor {

    public StructuredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public StructuredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public StructuredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public StructuredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    private List<StructuredRunnableFuture<?>> futures = new ArrayList<>();

    protected <T> StructuredFutureTask<T> newTaskFor(Runnable runnable, T value) {
        return new StructuredFutureTask<>(runnable, value);
    }

    protected <T> StructuredFutureTask<T> newTaskFor(Callable<T> callable) {
        return new StructuredFutureTask<>(callable);
    }

    /**
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     */
    public Future<?> add(Runnable task) {
        if (task == null) throw new NullPointerException();
        StructuredRunnableFuture<Void> ftask = newTaskFor(task, null);
        futures.add(ftask);
        return ftask;
    }

    /**
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     */
    public <T> Future<T> add(Runnable task, T result) {
        if (task == null) throw new NullPointerException();
        StructuredRunnableFuture<T> ftask = newTaskFor(task, result);
        futures.add(ftask);
        return ftask;
    }

    /**
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     */
    public <T> Future<T> add(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        StructuredRunnableFuture<T> ftask = newTaskFor(task);
        futures.add(ftask);
        return ftask;
    }

    public void submitAll() {
        futures.forEach((future) -> {
            future.attach(futures);
            execute(future);
        });
    }
}
