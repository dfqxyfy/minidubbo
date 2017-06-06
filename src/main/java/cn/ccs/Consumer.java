package cn.ccs;

import cn.ccs.communication.SocketConsumer;
import cn.ccs.log.log4j.ConfigLogger;
import cn.ccs.register.MUrl;
import cn.ccs.proxy.ProxyObject;
import cn.ccs.register.zookeeper.ZkRegister;
import cn.ccs.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class Consumer {
    private final static Logger LOGGER = LoggerFactory.getLogger(Consumer.class);
    public static void main(String[] args) throws IOException {
        ZkRegister zkRegister = new ZkRegister();
        zkRegister.init();

        MUrl mUrl = zkRegister.getRegister(HelloService.class.getName());

        SocketConsumer.init(mUrl.getHost(),mUrl.getPort());
        HelloService helloService = ProxyObject.getProxy(HelloService.class);
        try {
            for(int count = 0;count<10;count++) {
                String result = helloService.sayHello("cusumer say Hello ...."+count);
                LOGGER.info("receving from server:" + result);
                Thread.sleep(1000);
            }
            SocketConsumer.destroy();
            //System.in.read();
        } catch (InterruptedException e) {
            LOGGER.info("end",e);
        }
    }

}
