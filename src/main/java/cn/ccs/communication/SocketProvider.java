package cn.ccs.communication;

import cn.ccs.Constants;
import cn.ccs.Provider;
import cn.ccs.protocol.Protocol;
import cn.ccs.protocol.RegProtocol;
import cn.ccs.protocol.TransProtocol;

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

    public static void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", Integer.valueOf(Constants.SERVER_PORT));
        serverSocket.bind(socketAddress);

        final Socket socket = serverSocket.accept();

        InputStream inputStream = null;
        BufferedReader br = null;
        String receiveInfo = null;
        while (true) {
            try {
                inputStream = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(inputStream));
                TransProtocol tp = null;
                if ((receiveInfo = br.readLine()) != null) {
                    tp = Protocol.toTransProtocol(receiveInfo);
                }else {
                    continue;
                }
                System.out.println("receiving msg:" + receiveInfo);
                Object obj = Provider.objectMap.get(tp.getClassName());
                try {
                    //简化传值参数为String类型，并且只有一个参数
                    Method method = obj.getClass().getMethod(tp.getMethodName(), String.class);
                    try {
                        Object result = method.invoke(obj, tp.getParameter());
                        tp.setResult(result.toString());//默认为String
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
                out.write(tp.toString());
                out.write("\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
