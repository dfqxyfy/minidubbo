package cn.ccs.proxy;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class ProxyObject {

    public Object getProxy(Class<?>[] cls ){


        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), cls , new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(proxy,args);
            }
        });
    }

}
