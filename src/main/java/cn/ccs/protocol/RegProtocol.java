package cn.ccs.protocol;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class RegProtocol {
    private String host;
    private String port;
    private String className;

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

    @Override
    public String toString() {
        return
                "host=" + host  +
                ", port=" + port +
                ", className=" + className ;
    }
}
