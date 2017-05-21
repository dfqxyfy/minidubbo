package cn.ccs;

import cn.ccs.communication.SocketConsumer;
import cn.ccs.protocol.RegProtocol;
import cn.ccs.protocol.TransProtocol;
import cn.ccs.proxy.ProxyHelloServiceImpl;
import cn.ccs.register.Register;
import cn.ccs.service.HelloService;

import java.io.IOException;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class Consumer {
    public static void main(String[] args) {

        RegProtocol regProtocol = Register.getRegFile();
        SocketConsumer.init(regProtocol.getHost(),regProtocol.getPort());

        //使用静态代理访问，后面通过asm来动态生成HelloService子类，实现该功能
        HelloService helloService = new ProxyHelloServiceImpl();

        try {
            String result = helloService.sayHello("cusumer say Hello ....");
            System.out.println("receving from server:"+result);

            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
