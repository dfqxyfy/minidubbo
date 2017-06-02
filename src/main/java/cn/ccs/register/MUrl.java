package cn.ccs.register;

import cn.ccs.common.MException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ccs on 2017/5/28.
 */
public class MUrl {
    private String protocol;
    private String host;
    private String port;
    private String className;
    private Map<String, String> attributes = new HashMap<>();

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
    public String toString() {
        StringBuilder strb = new StringBuilder();
        if (protocol != null)
            strb.append(protocol).append("://");
        if (host != null) {
            strb.append(host);
            if (port != null)
                strb.append(":").append(port);
            strb.append("/");
        }
        if (className == null) {
            throw new MException("className 不能为空");
        }
        strb.append(className);
        if (attributes.size() > 0) {
            strb.append("?");
            strb.append(dealMap(attributes));
        }
        return strb.toString();
    }

    public String toUrlString() {
        try {
            return URLEncoder.encode(this.toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理的时候同时进行url编码
     */
    private String dealMap(Map<String, String> attributes) {
        if (attributes.size() == 0)
            return "";
        final Set<String> strings = attributes.keySet();
        StringBuffer strb = new StringBuffer();
        for (String s : strings) {
            try {
                strb.append(URLEncoder.encode(s, "utf-8"));
                strb.append("=");
                strb.append(URLEncoder.encode(attributes.get(s), "utf-8"));
                strb.append("&");
            } catch (UnsupportedEncodingException e) {
                throw new MException("url编码{1}失败", s);
            }
        }
        String result = strb.toString().substring(0, strb.length() - 1);
        return result;
    }

    private final static Pattern REG_URL_PATTERN = Pattern.compile("((\\w+)://)?(([\\w\\.]+)(:(\\d+))\\/)?([\\w\\.]+)(\\?(([\\w\\=\\%&]+)))?");

    /**
     * zk注册信息转换成MUrl类
     * @param str
     * @return MUrl
     */
    public static MUrl toMUrl(String str) {
        MUrl mUrl = new MUrl();
        String decode = null;
        try {
            decode = URLDecoder.decode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //String hostInfo = "";
        Matcher matcher = REG_URL_PATTERN.matcher(decode);
        String attributes = "";

        if (matcher.find()) {
            mUrl.setProtocol(matcher.group(2));
            mUrl.setHost(matcher.group(4));
            mUrl.setPort(matcher.group(6));
            mUrl.setClassName(matcher.group(7));
            attributes = matcher.group(9);
        }
        String[] params = attributes.split("&");
        for (String p : params) {
            String[] split1 = p.split("\\=");
            try {
                mUrl.getAttributes().put(URLDecoder.decode(split1[0], "utf-8"), URLDecoder.decode(split1[1], "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return mUrl;
    }
}
