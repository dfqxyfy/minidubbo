package cn.ccs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class HelloServiceImpl implements HelloService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String sayHello(String hello) {
        LOGGER.info("服务调用:" + hello);
        String result = "[service have executed]";
        return result;
    }
}
