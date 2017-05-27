package cn.ccs;

import cn.ccs.communication.SocketConsumer;
import cn.ccs.protocol.RegProtocol;
import cn.ccs.proxy.ProxyObject;
import cn.ccs.register.Register;
import cn.ccs.service.HelloService;

import java.io.IOException;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class Consumer {
    public static void main(String[] args)  {

        RegProtocol regProtocol = Register.getRegFile();
        SocketConsumer.init(regProtocol.getHost(),regProtocol.getPort());

        //使用静态代理访问，后面通过asm来动态生成HelloService子类，实现该功能
        HelloService helloService = ProxyObject.getProxy(HelloService.class);
        try {
            for(int count = 0;count<10;count++) {
                String result = helloService.sayHello("cusumer say Hello ...."+count);
                System.out.println("receving from server:" + result);
                Thread.sleep(1000);
            }
            SocketConsumer.destroy();
            //System.in.read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
