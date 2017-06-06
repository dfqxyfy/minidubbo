package communication;

import cn.ccs.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

    private static void start() throws IOException {
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ExThread.class);
    private Socket socket;
    ExThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            LOGGER.error("读取数据出错",e);
        }
        String receiveInfo = null;
        while (true) {
            try {
                if ((receiveInfo = br.readLine()) != null) {
                    OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
                    out.write("receiving msg:" + receiveInfo + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                LOGGER.error("消息错误", e);
            }
        }
    }
}
