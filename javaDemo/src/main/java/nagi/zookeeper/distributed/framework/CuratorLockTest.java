package nagi.zookeeper.distributed.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorLockTest {
    public static void main(String[] args) {
        //创建分布式锁1
        final InterProcessMutex lock1 = new InterProcessMutex(getCuratorFramework(), "/locks");

        //创建分布式锁2
        final InterProcessMutex lock2 = new InterProcessMutex(getCuratorFramework(), "/locks");

        new Thread(new Runnable() {
            public void run() {
                try {
                    lock1.acquire();
                    System.out.println("Thread1 acquire lock1");

                    lock1.acquire();
                    System.out.println("Thread1 acquire lock1 again");

                    lock1.release();
                    System.out.println("Thread1 release lock1");

                    lock1.release();
                    System.out.println("Thread1 release lock1 again");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    lock2.acquire();
                    System.out.println("Thread2 acquire lock2");

                    lock2.acquire();
                    System.out.println("Thread2 acquire lock2 again");

                    lock2.release();
                    System.out.println("Thread2 release lock2");

                    lock2.release();
                    System.out.println("Thread2 release lock2 again");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static CuratorFramework getCuratorFramework() {
        ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(3000, 3);

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.56.101:2181")
                .sessionTimeoutMs(20000)
                .connectionTimeoutMs(20000)
                .retryPolicy(exponentialBackoffRetry)
                .build();
        client.start();

        return client;
    }
}
