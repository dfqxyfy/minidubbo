package cn.ccs;

import cn.ccs.communication.SocketConsumer;
import cn.ccs.protocol.RegProtocol;
import cn.ccs.protocol.TransProtocol;
import cn.ccs.register.Register;

import java.io.IOException;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class Consumer {
    public static void main(String[] args) {

        RegProtocol regProtocol = Register.getRegFile();

        SocketConsumer.init(regProtocol.getHost(),regProtocol.getPort());

        TransProtocol tp = new TransProtocol();
        tp.setClassName("cn.ccs.service.HelloWorld");
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
