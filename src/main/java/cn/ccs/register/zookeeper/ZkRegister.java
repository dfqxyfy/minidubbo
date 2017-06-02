package cn.ccs.register.zookeeper;

import cn.ccs.common.MException;
import cn.ccs.register.MUrl;
import cn.ccs.register.Register;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
public class ZkRegister implements Register {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkRegister.class);
    //ZooKeeper
    private ZooKeeper zookeeper;

    private final String basicPath = "/mdubbo";

    private String connectString = null;
    private static final int sessionTimeout = 30000;

    //注册在zk中使用 /mdubbo/cn.xxxService/mUrl的方式
    @Override
    public void register(MUrl mUrl) {
        createPath(genUrl(mUrl));
    }

    @Override
    public MUrl getRegister(String className) {
        List<String> children;
        try {
            children = zookeeper.getChildren(basicPath + "/" + className, false);
        } catch (KeeperException | InterruptedException e) {//客户端获取异常
            LOGGER.error("客户端获取类{}出错", className, e);
            throw new MException("客户端获取类{1}出错", className);
        }
        Random random = new Random();
        int rand = random.nextInt(children.size());
        String regStr = children.get(rand);
        return MUrl.toMUrl(regStr);
    }

    private void createPath(String path) {
        String[] strs = path.split("/");
        StringBuilder mPath = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            if (s == null || "".equals(s.trim()))
                continue;
            mPath.append("/" + s);
            CreateMode cm = CreateMode.PERSISTENT;
            if (i == (strs.length - 1)) {
                cm = CreateMode.EPHEMERAL;
            }
            try {
                Stat stat = zookeeper.exists(mPath.toString(), false);
                if (stat == null) {

                    zookeeper.create(mPath.toString(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, cm);
                }
            } catch (KeeperException | InterruptedException e) {//服务注册异常
                LOGGER.error("服务器注册路径{}出错", mPath, e);
                throw new MException("服务器注册路径{1}出错", mPath.toString());
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
