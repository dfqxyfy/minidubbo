package cn.ccs;

import cn.ccs.common.Constants;
import cn.ccs.communication.SocketProvider;
import cn.ccs.log.log4j.ConfigLogger;
import cn.ccs.register.MUrl;
import cn.ccs.register.zookeeper.ZkRegister;
import cn.ccs.service.HelloService;
import cn.ccs.service.HelloServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class Provider {
    private static final Logger LOGGER = LoggerFactory.getLogger(Provider.class);
    public static Map<String,Object> objectMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        //ConfigLogger.initLog();

        MUrl mUrl = new MUrl();
        mUrl.setPort(Constants.SERVER_PORT);
        mUrl.setProtocol("http");
        mUrl.setClassName(HelloService.class.getName());
        mUrl.setHost("127.0.0.1");
        mUrl.getAttributes().put("a","b");
        mUrl.getAttributes().put("a=b","1=1");
        ZkRegister zkRegister = new ZkRegister();
        zkRegister.init();
        zkRegister.register(mUrl);

        HelloService helloService = new HelloServiceImpl();
        objectMap.put("cn.ccs.service.HelloService",helloService);
        start();
        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.info("end",e);
        }
    }


    private static void start(){
        try {
            //监听服务
            SocketProvider.start();
            System.in.read();
        } catch (IOException e) {
            LOGGER.error("启动异常",e);
        }
    }
}
