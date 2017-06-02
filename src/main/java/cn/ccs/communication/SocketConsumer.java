package cn.ccs.communication;

import cn.ccs.protocol.Protocol;
import cn.ccs.protocol.TransProtocol;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class SocketConsumer {

    private static Socket socket = null;
    private static OutputStreamWriter out = null;
    private static BufferedReader br =  null;

    private static Map<String,ResThread> resMap = new ConcurrentHashMap<>();

    public static void init(String host,String port) throws IOException {
        if(socket != null){
            return;
        }
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, Integer.valueOf(port)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        out = new OutputStreamWriter(socket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listenResult();
    }

    public static void destroy(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendMessage(TransProtocol tp) throws IOException {
        ResThread res = new ResThread();
        resMap.put(tp.getId(),res);
        out.write(tp.toString());
        out.write("\n");
        out.flush();

        String result = null;
        try {
            resMap.put(tp.getId(),res);
            synchronized (res.getLock()) {
                res.getLock().wait(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = res.getResult();
        //resMap.remove(tp.getId());
        return result;

    }

    public static void listenResult(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String receiveInfo = null;
                try {
                    while ((receiveInfo = br.readLine()) != null) {
                        System.out.println(receiveInfo);
                        try {
                            TransProtocol tp = Protocol.toProtocol(receiveInfo, TransProtocol.class);
                            System.out.println(tp);
                            if(tp.getId()!=null) {
                                ResThread res = resMap.get(tp.getId());
                                synchronized (res.getLock()) {
                                    res.setResult(tp.getResult());
                                    res.getLock().notify();
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            continue;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static class ResThread{
        private Object lock = new Object();
        private String result;

        public Object getLock() {
            return lock;
        }
        public void setLock(Object lock) {
            this.lock = lock;
        }
        public String getResult() {
            return result;
        }
        public void setResult(String result) {
            this.result = result;
        }
    }
}
