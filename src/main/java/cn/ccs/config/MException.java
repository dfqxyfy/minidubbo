package cn.ccs.config;

import java.text.Format;
import java.text.MessageFormat;
import java.util.Formatter;

/**
 * Created by ccs on 2017/6/2.
 */
public class MException extends RuntimeException{

    public MException(String msg){
        super(msg);
    }
    public MException(String msg,String... s){
        super(MessageFormat.format(msg,s));
    }
}
