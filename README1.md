1. 最基本的实现provider 向注册register注册服务，并在consumer消费服务（实现时暂时不引入其他包）
    register,暂时使用临时文件代替注册中心
    provider与consumer使用基本socket通信

basic_版本存在问题:
通信使用socket, 同步方式,inputStream和outputStream有依赖关系,所以当有多个客户端通信的时候可能会出bug
改进方式:
1>. 线程监听Consumer的inputStream, 对Provider,仍然保持同步调用
2>. write通信时,改为线程等待,然后,等消息返回后,使用回调通知原调用线程重新执行
3>. 为了实现2的效果,改进传输协议内容,为每一次传递消息增加一个id(使用uuid),区别消息调用的线程

这里做到了可以同时多consumer消费使用,目前版本可以作为实现目标功能的基础版本
 

