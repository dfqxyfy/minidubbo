package cn.ccs.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class Protocol {
    private final static Logger LOGGER = LoggerFactory.getLogger(Protocol.class);
    public static Map<String, String> getMap(String info) {
        Map<String, String> map = new HashMap<>();
        String[] ss = info.split(",");
        for (String s : ss) {
            String[] inners = s.split("=");
            map.put(inners[0].trim(), inners[1].trim());
        }
        return map;
    }

    public static <T> T toProtocol(String info, Class<T> t) {
        return (T) setValue(t, getMap(info));
    }

    private static Object setValue(Class<?> t, Map<String, String> map) {

        Object obj = null;
        try {
            obj = t.newInstance();
        } catch (InstantiationException|IllegalAccessException e) {
            LOGGER.error("初始化类{}错误",t,e);
        }
        Method[] methods = t.getMethods();
        for (Method m : methods) {
            if (!m.getName().startsWith("set"))
                continue;
            String methodName = m.getName().substring(3, 4).toLowerCase() + m.getName().substring(4);
            if (map.get(methodName) != null) {
                try {
                    m.invoke(obj, map.get(methodName));
                } catch (IllegalAccessException|InvocationTargetException e) {
                    LOGGER.error("执行类{}的方法{}错误",t,methodName,e);
                }
            }
        }
        return obj;
    }
}
