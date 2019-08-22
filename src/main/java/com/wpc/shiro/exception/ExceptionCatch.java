package com.wpc.shiro.exception;

import com.wpc.shiro.model.CommonCode;
import com.wpc.shiro.model.ResultCode;
import com.wpc.shiro.model.Results;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 * 缺点：只能处理 controller 层抛出的异常，对例如 Interceptor（拦截器）层的异常、定时任务中的异常、异步方法中的异常，不会进行处理。
 *       在filter内发生的异常，@ExceptionHandler是截获不到的。
 **/
@ControllerAdvice//控制器增强
public class ExceptionCatch {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    /**
     * 捕获CustomException此类异常
     * @param customException
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Results customException(CustomException customException){
        //记录日志
        LOGGER.error("catch exception:{}",customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return new Results(resultCode);
    }

    /**
     * 捕获Exception此类异常
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Results exception(Exception exception){
        //记录日志
        LOGGER.error("catch exception:{}",exception.getMessage());
        exception.printStackTrace();
        return new Results(CommonCode.SERVER_ERROR);
    }

    /**
     * 捕捉shiro的异常
     * @param exception
     * @return
     */
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public Results handleShiroException(ShiroException exception) {
        LOGGER.error("catch exception:{}", CommonCode.NOT_AUTHORIZED.message());
        return new Results(CommonCode.NOT_AUTHORIZED);
    }

    /**
     * 处理所有接口数据验证异常
     * * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Results handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        LOGGER.error("catch exception:{}",exception.getMessage());
        exception.printStackTrace();
        return new Results(CommonCode.PARAM_EXISTS_NULL);
    }

}
