package cn.ccs;

import cn.ccs.communication.SocketConsumer;
import cn.ccs.protocol.RegProtocol;
import cn.ccs.protocol.TransProtocol;
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


        //此处客户端动态调用方法暂时HelloService.sayHello(); 后面通过asm来动态生成HelloService子类，实现该功能
        TransProtocol tp = new TransProtocol();
        tp.setClassName("cn.ccs.service.HelloService");
        tp.setMethodName("sayHello");
        tp.setParameter("hello Server...");


        try {
            SocketConsumer.sendMessage(tp.toString());

            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
