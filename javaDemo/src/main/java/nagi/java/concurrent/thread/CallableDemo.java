package nagi.java.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {
    public static void main(String[] args) {
        //with class
        new Thread(new ThreadWithRun(), "AA").start();

        //with lambda
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            FutureTask<Integer> futureTask = new FutureTask<>(() -> 1000);
            Thread thread = new Thread(futureTask);
            thread.start();

            while (!futureTask.isDone()) {
                System.out.println("wait...");
            }

            try {
                System.out.println(futureTask.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, "BB").start();
    }

    static class ThreadWithRun implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            //未来任务 主线程构建新新线程处理并返回结果
            FutureTask<Integer> futureTask = new FutureTask<>(new ThreadWithCall());
            Thread thread = new Thread(futureTask);
            thread.start();

            while (!futureTask.isDone()) {
                System.out.println("wait...");
            }
            try {
                System.out.println(futureTask.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadWithCall implements Callable<Integer> {

        @Override
        public Integer call() {
            return 100;
        }
    }
  }
