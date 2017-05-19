本例从基本rpc调用来一步步实现一个mini dubbo
主要模块：provider register consumer 
1. 最基本的实现provider 向注册register注册服务，并在consumer消费服务（实现时暂时不引入其他包）
    register,暂时使用临时文件代替注册中心
    provider与consumer使用基本socket通信
    
2. 扩展provider和consumer
    使provider和consumer有分组属性，有重试次数，超时时间等的配置
    
3. 扩展服务注册者：
    可以使用zookeeper其他注册服务，consumer可以动态随机获取provider

通过上面就是一个最基本的分布式注册服务，下面对各个方面进行优化
 
4. consumer使用provider的路由规则
5. 服务可实现同步异步调用
6. consumer和provider通信扩展，可以使用http，Dubbo协议，Hessian协议等
7. 支持使用netty通信
8. 集成spring配置，并支持配置文件配置provider和consumer
