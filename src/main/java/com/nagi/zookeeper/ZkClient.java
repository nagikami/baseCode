package com.nagi.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ZkClient {

     private String connectString = "192.168.56.101:2181";
     private int sessionTimeout = 20000;
     private ZooKeeper zkClient;

     @Before
     public void init() throws IOException {
          zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
               //process of listener 注册监听器时也会调用
               public void process(WatchedEvent watchedEvent) {
                    System.out.println(watchedEvent.toString());
                    System.out.println("wrapper: " + watchedEvent.getWrapper());
               }
          });
     }

     @Test
     public void create() throws KeeperException, InterruptedException, IOException {
          String node1 = zkClient.create("/test", "hello".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
     }

     //register -> state changed -> watcher -> process function
     @Test
     public void getChildren() throws KeeperException, InterruptedException {
          List<String> children = zkClient.getChildren("/test", true);

          for (String child : children) {
               System.out.println(child);
          }

          //延时
          Thread.sleep(Long.MAX_VALUE);
     }

     @Test
     public void exist() throws KeeperException, InterruptedException {
          Stat exists = zkClient.exists("/test", false);

          System.out.println(exists == null ? "Does not Exist" : "exists");
     }
}
