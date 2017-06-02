package cn.ccs.proxy;

import cn.ccs.communication.SocketConsumer;
import cn.ccs.protocol.TransProtocol;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class ProxyObject {

    public static <T> T getProxy(Class<?> cls ){


        return (T)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{cls} , new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //if(method.getName().equals("sayHello")) {
                TransProtocol tp = new TransProtocol();
                tp.setId(UUID.randomUUID().toString().replace("-",""));
                tp.setClassName(cls.getName());
                tp.setMethodName(method.getName());
                tp.setParameter(args[0].toString());
                return SocketConsumer.sendMessage(tp);
            }
        });
    }

}
