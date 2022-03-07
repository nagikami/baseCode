package nagi.zookeeper.distributed.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DistributeLock {

    private String connectString = "192.168.56.101:2181";
    private int sessionTimeout = 20000;
    private final ZooKeeper zk;
    private CountDownLatch connectLatch = new CountDownLatch(1);
    private String waitPath;
    private CountDownLatch waitLatch = new CountDownLatch(1);
    private String currentNode;

    public DistributeLock() throws IOException, InterruptedException, KeeperException {
        //获取连接
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                //如果连接上zk需要释放connectLatch
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    connectLatch.countDown();
                }

                //释放waitLatch
                if (watchedEvent.getType() == Event.EventType.NodeDeleted && watchedEvent.getPath().equals(waitPath)) {
                    waitLatch.countDown();
                }
            }
        });

        connectLatch.await();
        //判断根节点/locks是否存在
        Stat exists = zk.exists("/locks", false);
        if (exists == null) {
            try {
                zk.create("/locks", "locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }

    //加锁
    public void lock() {
        //创建临时的带序号的节点
        try {
            currentNode = zk.create("/locks/seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

            //判断创建的节点的序号是不是最小的
            List<String> children = zk.getChildren("/locks", false);
            if (children.size() == 1) {
                return;
            } else {
                Collections.sort(children);

                int index = children.indexOf(currentNode.substring("/locks/".length()));

                if (index == -1) {
                    System.out.println("数据异常");
                } else if (index == 0) {
                    return;
                } else {
                    //需要监听前一个节点的变化
                    waitPath = "/locks/" + children.get(index - 1);
                    try {
                        zk.getData(waitPath, true, null);
                    } catch (KeeperException.NoNodeException e) {
                        System.out.println(waitPath + " is deleted");
                        return;
                    }

                    waitLatch.await();

                    return;
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //释放锁
    public void unLock() {
        try {
            zk.delete(currentNode, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
