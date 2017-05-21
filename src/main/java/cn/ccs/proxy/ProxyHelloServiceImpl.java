package cn.ccs.proxy;

import cn.ccs.communication.SocketConsumer;
import cn.ccs.protocol.TransProtocol;
import cn.ccs.service.HelloService;

import java.io.IOException;
import java.lang.reflect.AnnotatedType;

/**
 * Created by ccs on 2017/5/21.
 */
public class ProxyHelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String hello) {

        TransProtocol tp = new TransProtocol();
        this.getClass().getAnnotatedInterfaces();
        tp.setClassName(this.getClass().getInterfaces()[0].getName());
        tp.setMethodName("sayHello");
        tp.setParameter(hello);
        try {
            return SocketConsumer.sendMessage(tp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ProxyHelloServiceImpl p = new ProxyHelloServiceImpl();
        Class<?>[] interfaces = ProxyHelloServiceImpl.class.getInterfaces();
        for(Class c:interfaces){
            System.out.println(".............");
            System.out.println(c.getName());
            System.out.println(".............");
        }
    }
}
