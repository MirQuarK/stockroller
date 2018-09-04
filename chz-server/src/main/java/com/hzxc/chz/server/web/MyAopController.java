package com.hzxc.chz.server.web;

import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.server.annotation.*;
import com.hzxc.chz.server.config.MyAopConfig;
import com.hzxc.chz.server.aop.myaop.impl.AopImpl;
import com.hzxc.chz.server.aop.myaop.AopInter;
import com.hzxc.chz.server.utils.MyAopInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.*;

@RestController
public class MyAopController {

    private static Logger logger = LoggerFactory.getLogger(MyAopController.class);

    @AopBeforeTest
    public void testBefore() {
        logger.info("in myTest testBefore");
    }

    @AopAfterTest
    public void testAfter() {
        logger.info("in myTest testAfter");
    }

    @AopAroundTest
    public void testAround() {
        logger.info("in myTest testAround");
    }

    @AopTest
    public JsonResult test() {
        logger.info("in myTest test");
        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }

    @AopTest
    @RequestMapping(value = "myTest1", produces = "application/json")
    public JsonResult myTest1(HttpServletRequest request) {
        logger.info("in myTest myTest1");
        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }

    @CheckLogin
    @RequestMapping(value = "myTest2", produces = "application/json")
    public JsonResult myTest2(HttpServletRequest request) {
        logger.info("in myTest myTest2");
        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }

    @RequestMapping(value = "myTest", produces = "application/json")
    public JsonResult myTest(HttpServletRequest request) {
//        test();
        aopCall("test", null);

        return new JsonResult().setCode(ResultCodeEnum.SUCCESS).msg("success");
    }

    // 模拟，根据函数名判断调用切入，args先不用。需要时根据args区分重载函数
    public void aopCall(String name, Object[] args) {
        MyAopInfo myAopInfo = MyAopConfig.aopInfoMap.get(name);
        if(myAopInfo != null) {
            AopProxy aopProxy = new AopProxy();
            AopInter aopInter = (AopInter)aopProxy.bind(new AopImpl(), myAopInfo);

            for(MyAopInfo mi : MyAopConfig.aopBeforeMap.values()) {
                aopProxy.setBefore(mi.getCodeMethod());
                aopProxy.setBeforeT(mi.getClassInstance());
            }

            aopInter.voidf();
        }
    }

    static class AopProxy implements InvocationHandler {
        private Object target;
        private Parameter[] allparameters = null;

        private Method before = null;

        private Object beforeT;
        private Method after = null;
        private Object afterT;
        private MyAopInfo myAopInfo;

        public Method getBefore () {
            return before;
        }

        public void setBefore (Method before) {
            this.before = before;
        }

        public Object getBeforeT () {
            return beforeT;
        }

        public void setBeforeT (Object beforeT) {
            this.beforeT = beforeT;
        }

        public Object bind(Object target, MyAopInfo aopInfo) {
            this.target = target;
            myAopInfo = aopInfo;

            //取得代理对象
            return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(), this);   //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            Object result = null;

            if(before != null) {
                before.invoke(beforeT);
            }

            result = myAopInfo.process();

            if(after != null) {
                after.invoke(afterT);
            }

            return result;
        }
    }
}
