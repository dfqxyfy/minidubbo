package cn.ccs.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ccs on 2017/5/28.
 */
public class MUrl {
    private String protocol;
    private String host;
    private String port;
    private String className;
    private Map<String,String> attributes = new HashMap<>();

    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public Map<String, String> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
    @Override
    public String toString(){
        StringBuilder strb = new StringBuilder();
        if(protocol != null)
            strb.append(protocol).append("://");
        if(host != null) {
            strb.append(host);
            if(port != null)
                strb.append(":").append(port);
            strb.append("/");
        }
        if(className == null){
            throw new MException("className 不能为空");
        }
        strb.append(className);
        if(attributes.size()>0){
            strb.append("?");
            strb.append(dealMap(attributes));
        }
        return strb.toString();
    }

    //处理的时候同时进行url编码
    private String dealMap(Map<String, String> attributes) {
        if(attributes.size() == 0)
            return "";
        final Set<String> strings = attributes.keySet();
        StringBuffer strb = new StringBuffer();
        for(String s:strings){
            try {
                strb.append(URLEncoder.encode(s,"utf-8"));
                strb.append("=");
                strb.append(URLEncoder.encode(attributes.get(s),"utf-8"));
                strb.append("&");
            } catch (UnsupportedEncodingException e) {
                throw new MException("url编码{1}失败",s);
            }
        }
        String result = strb.toString().substring(0,strb.length()-1);
        return result;
    }

}
