package com.wpc.shiro.exception;


import com.wpc.shiro.model.ResultCode;

/**
 * 自定义异常类型
 **/
public class CustomException extends RuntimeException {

    //错误代码
    ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
    public ResultCode getResultCode(){
        return resultCode;
    }

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
