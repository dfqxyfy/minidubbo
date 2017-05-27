package cn.ccs.protocol;

import java.util.List;

/**
 * Created by chaichuanshi on 2017/5/19.
 */
public class TransProtocol {

    private String id;
    private String className;
    private String methodName;
    private String parameter;//简化参数,为1个String类型
    private String result;  //简化返回的类型

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", className=" + className +
                ", methodName=" + methodName +
                ", parameter=" + parameter +
                ", result=" + result
                ;
    }
}
