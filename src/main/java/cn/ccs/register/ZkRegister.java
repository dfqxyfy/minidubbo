package cn.ccs.register;

import cn.ccs.config.MUrl;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

/**
 * Created by ccs on 2017/6/2.
 * zookeeper注册中心
 * 功能:1.注册url
 * 2.获取注册的url
 * <p>
 * 实现方式:引入zookeeper客户端
 */
public class ZkRegister {
    //ZooKeeper
    private ZooKeeper zookeeper;

    private final String basicPath = "/mdubbo";

    private String connectString = null;
    private final int sessionTimeout = 30000;

    //注册在zk中使用 /mdubbo/cn.xxxService/mUrl的方式
    public void register(MUrl mUrl) {
        createPath(genUrl(mUrl));
    }

    public MUrl getRegister(String className) {
        try {
            final List<String> children = zookeeper.getChildren(basicPath + "/" + className, false);
            Random random = new Random();
            int rand = random.nextInt(children.size());
            String regStr = children.get(rand);
            MUrl mUrl = MUrl.toMUrl(regStr);

            return mUrl;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createPath(String path) {
        String[] strs = path.split("/");
        StringBuilder mPath = new StringBuilder();
        for (String s : strs) {
            try {
                if (s == null || "".equals(s.trim()))
                    continue;
                mPath.append("/" + s);
                Stat stat = zookeeper.exists(mPath.toString(), false);
                if (stat == null) {
                    zookeeper.create(mPath.toString(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private String genUrl(MUrl mUrl) {
        StringBuilder strb = new StringBuilder();
        strb.append(basicPath).append("/").append(mUrl.getClassName()).append("/").append(mUrl.toUrlString());
        return strb.toString();
    }

    public void init() throws IOException {
        if (connectString == null)
            connectString = "127.0.0.1:2181";
        zookeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event.getState());
            }
        });
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }
}
