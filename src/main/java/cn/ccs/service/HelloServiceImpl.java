package cn.ccs.service;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String hello) {
        System.out.println("service receive:"+hello);
        return hello + "world";
    }
}