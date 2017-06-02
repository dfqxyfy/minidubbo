package cn.ccs.register;

import cn.ccs.config.MUrl;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by ccs on 2017/6/2.
 * zookeeper注册中心
 * 功能:1.注册url
 * 2.获取注册的url
 *
 * 实现方式:引入zookeeper客户端
 */
public class ZkRegister {
    //ZooKeeper
    private ZooKeeper zookeeper;

    private final String basicPath ="/mdubbo";
    //注册在zk中使用 /mdubbo/cn.xxxService/mUrl的方式
    public void register(MUrl mUrl) {
        try {
            zookeeper.create(genUrl(mUrl), null, null, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getRegister(String className){
        try {
            final List<String> children = zookeeper.getChildren(basicPath + "/" + className, true);
            Random random = new Random(children.size());
            int rand = random.nextInt();
            return children.get(rand);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String genUrl(MUrl mUrl){
        StringBuilder strb = new StringBuilder();
        strb.append(basicPath).append("/").append(mUrl.getClassName()).append("/").append(mUrl.toString());
        return strb.toString();
    }

    private void init() throws IOException {
        String connectString = "127.0.0.1:2181" ;
        int sessionTimeout = 1000;
        zookeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event.getState());
            }
        });
    }
}
