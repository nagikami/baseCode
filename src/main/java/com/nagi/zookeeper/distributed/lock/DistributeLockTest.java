package com.nagi.zookeeper.distributed.lock;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class DistributeLockTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DistributeLock distributeLock = new DistributeLock();
                    distributeLock.lock();
                    System.out.println("Thread1 acquire lock");
                    //Thread.sleep(100);
                    distributeLock.unLock();
                    System.out.println("Thread1 unlock");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DistributeLock distributeLock = new DistributeLock();
                    distributeLock.lock();
                    System.out.println("Thread2 acquire lock");
                    distributeLock.unLock();
                    System.out.println("Thread2 unlock");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
