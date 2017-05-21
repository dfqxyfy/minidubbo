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
            //new ExThread(socket.getInputStream()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendMessage(String msg) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
        out.write(msg);
        out.write("\n");
        out.flush();

        BufferedReader br = null;
        String receiveInfo = null;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        StringBuilder strb = new StringBuilder();
        try {
            while ((receiveInfo = br.readLine()) != null) {
                System.out.println(receiveInfo);
                strb.append(receiveInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  strb.toString();
    }



}
