package nagi.java.concurrent.structured;

import java.util.concurrent.*;

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
            StructuredThreadPoolExecutor structuredThreadPoolExecutor;
            try {
                structuredThreadPoolExecutor = new StructuredThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<>());
                Future<?> future1 = structuredThreadPoolExecutor.add(this.subTask1);
                Future<?> future2 = structuredThreadPoolExecutor.add(this.subTask2);
                structuredThreadPoolExecutor.submitAll();
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
