package com.nagi.zookeeper.case1;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DistributeClient {

    private String connectString = "192.168.56.101:2181";
    private int sessionTimeout = 20000;
    private ZooKeeper zk;
    private Set<String> servers = new HashSet<>();

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeClient client = new DistributeClient();

        //获取连接
        client.getConnect();

        //业务
        client.business();
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    //获取服务器列表
    private void getServers() throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren("/servers", true);

        servers.clear();
        for (String child : children) {
            byte[] data = zk.getData("/servers/" + child, false, null);
            servers.add(new String(data));
        }
        System.out.println(servers);
    }

    private void getConnect() throws IOException {
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getServers();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
