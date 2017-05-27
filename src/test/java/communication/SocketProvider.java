package communication;

import cn.ccs.Constants;
import cn.ccs.Provider;
import cn.ccs.protocol.Protocol;
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
    private static ServerSocket serverSocket = null;
    private static void init() throws IOException {
        serverSocket = new ServerSocket();
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", Integer.valueOf(Constants.SERVER_PORT));
        serverSocket.bind(socketAddress);
    }

    public static void start() throws IOException {
        while(true){
            Socket accept = serverSocket.accept();
            new ExThread(accept).start();
        }
    }
    public static void main (String[]args) throws IOException {
        init();
        start();
    }
}
class ExThread extends Thread{
    private Socket socket;
    public ExThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String receiveInfo = null;
        while (true) {
            try {
                System.out.println("receiving msg:" + receiveInfo);
                if ((receiveInfo = br.readLine()) != null) {
                    OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
                    out.write("receiving msg:" + receiveInfo + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
