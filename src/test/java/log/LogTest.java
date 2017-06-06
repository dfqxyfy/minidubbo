package log;

import cn.ccs.log.log4j.ConfigLogger;
import org.apache.log4j.Priority;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by chaichuanshi on 2017/6/6.
 */
public class LogTest {

    @Test
    public void logTest(){
        //ConfigLogger.initLog();
        Logger logger = LoggerFactory.getLogger(LogTest.class);
        logger.debug("2Start of the main()");
        logger.info("2Just testing a log message with priority set to INFO");
        logger.warn("2Just testing a log message with priority set to WARN");
        String aa = "3333";
        Object bb = "4444";
        Object cc = "5555";
        logger.error("aaa{}a{}aa{}aaaa",aa,bb,cc,new RuntimeException());
    }
}
