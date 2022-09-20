package nagi.java.concurrent.structured;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

public class StructuredFutureTaskManager {
    private ExecutorService executorService;

    public StructuredFutureTaskManager(ExecutorService executorService) {
        this.executorService = executorService;
    }

    private List<StructuredRunnableFuture<?>> futures = new ArrayList<>();

    protected <T> StructuredFutureTask<T> newTaskFor(Runnable runnable, T value) {
        return new StructuredFutureTask<>(runnable, value);
    }

    protected <T> StructuredFutureTask<T> newTaskFor(Callable<T> callable) {
        return new StructuredFutureTask<>(callable);
    }

    /**
     * 提交关联任务
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     */
    public StructuredRunnableFuture<?> submit(Runnable task) {
        if (task == null) throw new NullPointerException();
        StructuredRunnableFuture<Void> ftask = newTaskFor(task, null);
        futures.add(ftask);
        return ftask;
    }

    /**
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     */
    public <T> StructuredRunnableFuture<T> submit(Runnable task, T result) {
        if (task == null) throw new NullPointerException();
        StructuredRunnableFuture<T> ftask = newTaskFor(task, result);
        futures.add(ftask);
        return ftask;
    }

    /**
     * @throws RejectedExecutionException {@inheritDoc}
     * @throws NullPointerException       {@inheritDoc}
     */
    public <T> StructuredRunnableFuture<T> submit(Callable<T> task) {
        if (task == null) throw new NullPointerException();
        StructuredRunnableFuture<T> ftask = newTaskFor(task);
        futures.add(ftask);
        return ftask;
    }

    public void submitAll() {
        futures.forEach((future) -> future.attach(futures.stream().filter(f -> future != f).collect(Collectors.toList())));
    }

    public void executeAll() {
        futures.forEach((future) -> this.executorService.execute(future));
    }
}
