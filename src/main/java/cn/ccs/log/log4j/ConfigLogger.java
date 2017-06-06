package cn.ccs.log.log4j;

import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

/**
 * Created by chaichuanshi on 2017/6/2.
 */
public class ConfigLogger {

    public static void initLog(){

        Properties properties = new Properties();
        properties.setProperty("log4j.rootLogger", "application");
        properties.setProperty("log4j.appender.application", "org.apache.log4j.DailyRollingFileAppender");
        properties.setProperty("log4j.appender.application.File", "D:/temp.log");
        properties.setProperty("log4j.appender.application.Append", "true");
        properties.setProperty("log4j.appender.application.DatePattern", "'.'yyyy-MM-dd");
        properties.setProperty("log4j.appender.application.layout", "org.apache.log4j.PatternLayout");
        properties.setProperty("log4j.appender.application.layout.ConversionPattern", "%d [%t] %-5p %C{6} (%F:%L) - %m%n");
        //PropertyConfigurator.configure(properties);
    }
}
