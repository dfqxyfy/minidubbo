package register;

import cn.ccs.config.MUrl;
import cn.ccs.register.ZkRegister;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by ccs on 2017/6/2.
 */
public class ZkRegisterTest {
    @Test
    public void zkTest() throws IOException {
        ZkRegister register = new ZkRegister();
        register.init();

        MUrl mUrl = new MUrl();
        mUrl.setPort("8888");
        mUrl.setProtocol("http");
        mUrl.setClassName("cn.ccs.TestClass");
        mUrl.setHost("127.0.0.1");
        mUrl.getAttributes().put("a","b");
        mUrl.getAttributes().put("a=b","1=1");

        register.register(mUrl);
        //String str = register.getRegister("cn.ccs.TestClass");
        //System.out.println(str);
    }
}
