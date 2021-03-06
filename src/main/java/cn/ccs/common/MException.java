package cn.ccs.common;

import java.text.MessageFormat;

/**
 * 统一异常处理
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
