2. 扩展服务注册者：
    可以使用zookeeper其他注册服务，consumer可以动态随机获取provider

分析一下basic_1的问题:
注册服务使用字符串形式:host=xxx,port=xxx,classname=xxx
为了以后兼容各个协议,做成类似url的形式:mdubbo://host:port/classname?method=xxx&group=abcd
这种做法参照dubbo实现(对注册服务接触不多,不知道这种做法算不算比较流行的做法)
在zookeeper中的注册路径分析:
该服务是以接口为单位的服务,所以可以使用接口名称作为服务的路径,同时标识自己的身份provider
    路径可以是:/mdubbo/cn.xxx.xxService/provide/mdubbo://host:port?method=.....

    dubbo实际的注册路径这种形式:
    /dubbo/com.ccs.dubbo.DubboService/providers/dubbo%3A%2F%2F192.168.31.181%3A20880%2Fcom.ccs.dubbo.DubboService%3Fanyhost%3Dtrue%26application%3Danyname_provider%26dubbo%3D2.5.3%26interface%3Dcom.ccs.dubbo.DubboService%26methods%3DsayHello%26pid%3D8280%26side%3Dprovider%26timestamp%3D1495936879320
    url解码
    /dubbo/com.ccs.dubbo.DubboService/providers/dubbo://192.168.31.181:20880/com.ccs.dubbo.DubboService?anyhost=true&application=anyname_provider&dubbo=2.5.3&interface=com.ccs.dubbo.DubboService&methods=sayHello&pid=8280&side=provider&timestamp=1495936879320

