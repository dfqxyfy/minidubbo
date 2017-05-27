package communication;

import cn.ccs.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class SocketConsumer {

    private static Socket socket = null;
    public static void init(String host,String port){
        socket = new Socket();
        try {
            //socket.bind(new InetSocketAddress(host, Integer.valueOf(Constants.CONSUMER_PORT)));
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

        String receiveInfo = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuilder strb = new StringBuilder();
        try {
            if ((receiveInfo = br.readLine()) != null ) {
                System.out.println(receiveInfo);
                strb.append(receiveInfo);
                return strb.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return strb.toString();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        init("127.0.0.1",Constants.SERVER_PORT);
        int count = 1;
        for(;;count++) {
            sendMessage("client:" + count);
            Thread.sleep(1000);
        }
    }


}
