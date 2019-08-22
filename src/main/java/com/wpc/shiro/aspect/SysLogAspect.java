package com.wpc.shiro.aspect;

import com.alibaba.fastjson.JSON;
import com.wpc.shiro.annotation.SysLog;
import com.wpc.shiro.bean.Log;
import com.wpc.shiro.bean.User;
import com.wpc.shiro.service.LogService;
import com.wpc.shiro.utils.HttpContextUtils;
import com.wpc.shiro.utils.IPUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志，切面处理类
 */
@Log4j2
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.wpc.shiro.annotation.SysLog)")
    public void logPointCut() { }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        saveSysLog(point, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logInfo = new Log();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            //注解上的描述
            logInfo.setOperation(syslog.value());
        }
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        logInfo.setMethod(className + "." + methodName + "()");
        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSON.toJSONString(args[0]);
            logInfo.setParams(params);
        } catch (Exception e) {
            log.error("日志保存时，参数转json异常");
        }
        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        logInfo.setIp(IPUtils.getIpAddr(request));
        //用户名
        String username = ((User) SecurityUtils.getSubject().getPrincipal()).getUsername();
        logInfo.setUserName(username);
        logInfo.setTime(time);
        logInfo.setCreateDate(new Date());
        //保存系统日志
        logService.save(logInfo);
    }
}
