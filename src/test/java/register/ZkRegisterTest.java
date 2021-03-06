package register;

import cn.ccs.register.MUrl;
import cn.ccs.register.zookeeper.ZkRegister;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by ccs on 2017/6/2.
 */
public class ZkRegisterTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkRegisterTest.class);
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
        //LOGGER.info(str);
    }
}
