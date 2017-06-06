package cn.ccs.communication;

import cn.ccs.common.Constants;
import cn.ccs.Provider;
import cn.ccs.protocol.Protocol;
import cn.ccs.protocol.TransProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class SocketProvider {
    private final static Logger LOGGER = LoggerFactory.getLogger(SocketProvider.class);
    private static ServerSocket serverSocket = null;
    private static void init() throws IOException {
        if(serverSocket != null)
            return;
        serverSocket = new ServerSocket();
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", Integer.valueOf(Constants.SERVER_PORT));
        serverSocket.bind(socketAddress);
    }

    public static void start() throws IOException {
        init();
        while(true){
            Socket socket = serverSocket.accept();
            new ExThread(socket).start();
        }
    }


}
class ExThread extends Thread{
    private final static Logger LOGGER = LoggerFactory.getLogger(ExThread.class);
    private Socket socket;
    public ExThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {

        BufferedReader br = null;
        String receiveInfo = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            LOGGER.error("读写错误",e);
        }
        while (true) {
            try {
                TransProtocol tp = null;
                if ((receiveInfo = br.readLine()) != null) {
                    tp = Protocol.toProtocol(receiveInfo,TransProtocol.class);
                }else {
                    continue;
                }
                LOGGER.info("服务器收到消息:{}" , receiveInfo);
                invokeMethod(tp);

                OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
                out.write(tp.toString());
                out.write("\n");
                out.flush();
                LOGGER.info("服务器输出{}",tp);
            } catch (IOException e) {
                LOGGER.error("想服务器写入错误",e);
            }
        }
    }
    private static void invokeMethod(TransProtocol tp){
        Object obj = Provider.objectMap.get(tp.getClassName());
        Method method = null;
        try {
            //简化传值参数为String类型，并且只有一个参数
            method = obj.getClass().getMethod(tp.getMethodName(), String.class);
            try {
                Object result = method.invoke(obj, tp.getParameter());
                tp.setResult(result.toString());//默认为String
            } catch (IllegalAccessException|InvocationTargetException e) {
                LOGGER.error("方法{}执行错误",method);
            }
        } catch (NoSuchMethodException e) {
            LOGGER.error("方法{}不存在",tp.getMethodName(),e);
        }
    }
}
