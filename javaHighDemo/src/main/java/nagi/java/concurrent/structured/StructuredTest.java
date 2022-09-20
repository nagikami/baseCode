package nagi.java.concurrent.structured;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 管理并发子任务之间的关系，当子任务抛出异常时，应该取消所有关联的子任务，避免线程泄露
 */
public class StructuredTest {
    public static void main(String[] args) {
        new Task("task").execute();
    }

    static class Task {
        private String name;
        private SubTask subTask1 = new SubTask("subTask1") {
            @Override
            void execute() {
                System.out.println("Task " + this.name + " is running");
                throw new RuntimeException();
            }
        };

        private SubTask subTask2 = new SubTask("subTask2") {
            @Override
            void execute() {
                System.out.println("Task " + this.name + " is running");
                // 监控subTask1抛出异常时，调用的取消所有关联任务的行为
                while (true) {
                    if (Thread.interrupted()) {
                        System.out.println("cancel " + this.name);
                    }
                }
            }
        };

        public Task(String name) {
            this.name = name;
        }

        public void execute() {
            System.out.println("Task " + name + " is running");
            ExecutorService executorService;
            try {
                executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
                StructuredFutureTaskManager futureTaskManager = new StructuredFutureTaskManager(executorService);
                StructuredRunnableFuture<?> future1 = futureTaskManager.submit(this.subTask1);
                StructuredRunnableFuture<?> future2 = futureTaskManager.submit(this.subTask2);
                futureTaskManager.submitAll();
                // 可选，取消任务边
                //future1.removeEdge(future2);
                futureTaskManager.executeAll();
                future1.get();
                future2.get();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

        abstract static class SubTask implements Runnable {
            protected String name;

            public SubTask(String name) {
                this.name = name;
            }

            abstract void execute();

            @Override
            public void run() {
                execute();
            }
        }
    }
}
