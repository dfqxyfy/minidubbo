package config;

import cn.ccs.config.MUrl;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println(mUrl.toUrlString());
    }

    private static Pattern pattern = Pattern.compile("((\\w+)://)?(([\\w\\.]+)(:(\\d+))\\/)?([\\w\\.]+)(\\?(([\\w\\=\\%&]+)))?");
    @Test
    public void decode(){

        String str = "http://127.0.0.1:8080/cn.ccs.TestClass?a=b&a%3Db=1%3D1";
        str = "http://127.0.0.1:8080/cn.ccs.TestClass?a=b&a%3Db=1%3D1";
        str = "http%3A%2F%2F127.0.0.1%3A8888%2Fcn.ccs.TestClass%3Fa%3Db%26a%253Db%3D1%253D1";

        MUrl mUrl = MUrl.toMUrl(str);
        System.out.println(mUrl);
    }

}
