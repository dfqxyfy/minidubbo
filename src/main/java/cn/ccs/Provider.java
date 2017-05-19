package cn.ccs;

import cn.ccs.communication.SocketProvider;
import cn.ccs.protocol.RegProtocol;
import cn.ccs.register.Register;
import cn.ccs.service.HelloService;
import cn.ccs.service.HelloServiceImpl;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class Provider {
    public static Map<String,Object> objectMap = new ConcurrentHashMap<>();
    public static void main(String[] args) {

        //注册服务
        RegProtocol regProtocol = new RegProtocol();
        regProtocol.setHost("127.0.0.1");
        regProtocol.setPort(Constants.SERVER_PORT);
        regProtocol.setClassName("cn.ccs.service.HelloService");
        Register.register(regProtocol.toString());


        HelloService helloService = new HelloServiceImpl();
        objectMap.put("cn.ccs.service.HelloService",helloService);

        try {
            //监听服务
            SocketProvider.start();

            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
