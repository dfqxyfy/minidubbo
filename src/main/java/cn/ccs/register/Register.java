package cn.ccs.register;

import cn.ccs.protocol.Protocol;
import cn.ccs.protocol.RegProtocol;

import java.io.*;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class Register {
    private static String folder = System.getProperty("java.io.tmpdir");
    private static String regFile = "/minidubbo.reg";
    public static void register(String url){
        write(url);
    }
    public static RegProtocol getRegFile(){
        String str = read();
        return Protocol.toRegProtocol(str);
    }

    private static synchronized void write(String str){
        FileWriter fw = null;
        try {
            fw = new FileWriter(folder+regFile);
            fw.write(str);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static synchronized String read(){
        BufferedReader br = null;
        String regInfo = null;
        try {
            br = new BufferedReader(new FileReader(folder+regFile));
            regInfo = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return regInfo;
    }

}
