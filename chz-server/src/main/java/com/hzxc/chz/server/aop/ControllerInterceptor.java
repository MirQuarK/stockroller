package com.hzxc.chz.server.aop;

import com.alibaba.fastjson.JSON;
import com.hzxc.chz.common.Constant;
import com.hzxc.chz.common.enums.Enumes;
import com.hzxc.chz.common.enums.ResultCodeEnum;
import com.hzxc.chz.dto.JsonResult;
import com.hzxc.chz.server.annotation.CheckLogin;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 拦截器：记录用户操作日志
 *
 * @author chz
 */
@Aspect
@Component
public class ControllerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

    // 定义拦截规则：拦截com.hzxc.chz.server.web包下面的所有类中，有指定注解的方法。
    @Pointcut("execution(* com.hzxc.chz.server.web..*(..)) && @annotation(com.hzxc.chz.server.annotation.CheckLogin)")
    public void checkLoginPointcut() {
    }

    @Pointcut("execution(* com.hzxc.chz.server.web..*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMappingPointcut() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     * @throws Throwable
     */
    // 指定拦截器规则；也可以直接把定义的pointcut写进这里
    @Around("execution(* com.hzxc.chz.server.web..*(..)) && @annotation(checkLogin)")
    public Object interceptor(ProceedingJoinPoint pjp, CheckLogin checkLogin) throws Throwable {
        long beginTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod(); // 获取被拦截的方法
        String methodName = method.getName(); // 获取被拦截的方法名

        logger.info("请求开始，方法：{}", methodName);

        Object result = null;
        boolean havaRequestParam = false;

        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                havaRequestParam = true;
                HttpServletRequest request = (HttpServletRequest) arg;
                if(logger.isDebugEnabled()){
                    logger.debug("arg: HttpServletRequest[method={},parameters={}]",request.getMethod(), JSON.toJSONString(request.getParameterMap()));
                }

                // 查看登陆.
                if (request.getSession().getAttribute(Constant.SESSION_USER_ID_KEY) == null) {
                    //用户未登录或超时
                    result = new JsonResult<>().setCode(ResultCodeEnum.NOT_LOGIN);
                    request.getSession().invalidate();  // 未登陆消除老session，避免之后HttpSessionStrategy生成新cookie
                    break;
                }

                // 比较权限.
                int needRole = Enumes.UserTypeEnum.parseType(checkLogin.role()).val();
                String strUserRole = (String) request.getSession().getAttribute(Constant.SESSION_USER_ROLE);
                if(strUserRole == null || Enumes.UserTypeEnum.parseType(strUserRole) == null) {
                    result = new JsonResult<>().setCode(ResultCodeEnum.SERVER_ERROR).msg("user null role : " + strUserRole);
                    break;
                }
                int userRole =  Enumes.UserTypeEnum.parseType(strUserRole).val();
                if(needRole < userRole) {
                    result = new JsonResult<>().setCode(ResultCodeEnum.NO_PERMISSION);
                }
                break;
            }else{
                logger.debug("arg: {}", arg);
            }
        }

        // 想拦截却没有带会话参数.
        if(!havaRequestParam) {
            result = new JsonResult<>().setCode(ResultCodeEnum.SERVER_ERROR).msg("no HttpServletRequest param");
        }

        try {
            if (result == null) {
                // 一切正常的情况下，继续执行被拦截的方法
                result = pjp.proceed();
            }
        } catch (Throwable e) {
            logger.info("exception: ", e);
            result = new JsonResult<>().setCode(ResultCodeEnum.SERVER_ERROR).msg(e.getMessage());
        } finally {
            long costMs = System.currentTimeMillis() - beginTime;
            logger.info("{}请求结束，耗时：{}ms", methodName, costMs);
        }
        return result;
    }


}