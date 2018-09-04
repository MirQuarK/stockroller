package com.hzxc.chz.server.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * create by chz on 2018/8/25
 */


// 切入对象的信息
public class MyAopInfo {

    private Object classInstance = null;
    private Method codeMethod = null;
    private Parameter[] methodParameters;

    public Object getClassInstance () {
        return classInstance;
    }

    public void setClassInstance (Object classInstance) {
        this.classInstance = classInstance;
    }

    public Method getCodeMethod () {
        return codeMethod;
    }

    public void setCodeMethod (Method codeMethod) {
        this.codeMethod = codeMethod;
    }

    public Parameter[] getMethodParameters () {
        return methodParameters;
    }

    public void setMethodParameters (Parameter[] methodParameters) {
        this.methodParameters = methodParameters;
    }

    public Object process() {
        try {
            return codeMethod.invoke(classInstance, methodParameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}
