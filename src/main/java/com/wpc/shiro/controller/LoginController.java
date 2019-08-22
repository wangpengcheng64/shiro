package com.wpc.shiro.controller;

import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.user.UserUpdatePwdReq;
import com.wpc.shiro.pojo.res.user.UserRes;
import com.wpc.shiro.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
/**
 * @Author: 王鹏程
 * @Date: 2019/2/24 0024
 * @Time: 下午 5:29
 */
@Api(tags = "登陆管理", description = "LoginController")
@Log4j2
@RestController
@RequestMapping("/sys")
public class LoginController{

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Results login(String username, String password){
        log.info("用户登陆, request param:{}", username);
        if (StringUtils.isEmpty(username)){
            return Results.resultErr("登录名不能为空");
        }
        if (StringUtils.isEmpty(password)){
            return Results.resultErr("密码不能为空");
        }
        return loginService.login(username, password);
    }

    @ApiOperation(value = "用户密码修改")
    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    public Results updatePassword(@Validated @RequestBody UserUpdatePwdReq userUpdatePwdReq) {
        log.info("用户密码修改, request param:{}", userUpdatePwdReq);
        return loginService.updatePassword(userUpdatePwdReq);
    }

    @ApiOperation(value = "用户退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Results logout(){
        //将redis中的token删除即可
        return Results.resultSucc();
    }
}
