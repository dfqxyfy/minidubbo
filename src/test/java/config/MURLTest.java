package config;

import cn.ccs.config.MUrl;
import org.junit.Test;

/**
 * Created by ccs on 2017/6/2.
 */
public class MURLTest {
    @Test
    public void url2String(){
        MUrl mUrl = new MUrl();
        mUrl.setPort("8888");
        mUrl.setProtocol("http");
        mUrl.setClassName("cn.ccs.TestClass");
        mUrl.setHost("127.0.0.1");
        mUrl.getAttributes().put("a","b");
        mUrl.getAttributes().put("a=b","1=1");
        System.out.println(mUrl.toString());
    }
}
