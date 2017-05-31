package cn.ccs.config;

import java.util.HashMap;
import java.util.Map;

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
}
