package cn.ccs.register;

/**
 * Created by chaichuanshi on 2017/6/2.
 * 该接口保持2个功能 1.服务端注册接口 2.客户端获取注册信息接口
 */

public interface Register {
    void register(MUrl mUrl);
    MUrl getRegister(String className);
}
