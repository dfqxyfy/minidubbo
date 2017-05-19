package cn.ccs.protocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class Protocol<T> {

    public T toTransProtocol(String info){
        Map<String,String> map = new HashMap<>();
        String[] ss = info.split(",");
        for(String s:ss){
            String[] inners = s.split("=");
            map.put(inners[0].trim(),inners[1].trim());
        }
        T obj = setValue(map);
        return obj;
    }

    private T setValue(Map<String,String> map){

        Object obj = null;
        try {
            obj = T.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Method[] methods = t.getMethods();
        for(Method m:methods){
            if(!m.getName().startsWith("set"))
                continue;
            String methodName = m.getName().substring(3,4).toLowerCase()+m.getName().substring(4);
            if(map.get(methodName) != null){
                try {
                    m.invoke(obj,map.get(methodName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T)obj;
    }
}
