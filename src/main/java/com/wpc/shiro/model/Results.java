package com.wpc.shiro.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class Results<T> {

    private boolean success;

    private int code;

    private String message;

    private T result;

    public Results(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public Results(ResultCode resultCode, T result){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.result = result;
    }

    public static Results resultSucc(){
        return new Results(CommonCode.SUCCESS);
    }
    public static Results resultErr(){
        return new Results(CommonCode.FAIL);
    }

    public static Results resultSucc(Object result){
        return new Results(CommonCode.SUCCESS, result);
    }
    public static Results resultErr(Object result){
        return new Results(CommonCode.FAIL, result);
    }

    public static Results resultTokenErr(Object result){
        return new Results(CommonCode.INVALID_TOKEN, result);
    }

}
