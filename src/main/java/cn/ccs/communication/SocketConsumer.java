package cn.ccs.communication;

import cn.ccs.Constants;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class SocketConsumer {

    private static Socket socket = null;
    public static void init(String host,String port){
        socket = new Socket();
        try {
            socket.bind(new InetSocketAddress(host, Integer.valueOf(Constants.CONSUMER_PORT)));
            socket.connect(new InetSocketAddress(host, Integer.valueOf(port)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(String str) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
        out.write(str);
        out.write("\n");
        out.flush();
    }

    private class ExThread extends Thread{
        InputStream inputStream ;
        public ExThread(InputStream inputStream ){
            this.inputStream = inputStream;
        }
        @Override
        public void run(){
            BufferedReader br = null;
            String receiveInfo = null;
            br = new BufferedReader(new InputStreamReader(inputStream));
            try {
                receiveInfo = br.readLine();
                System.out.println(receiveInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
