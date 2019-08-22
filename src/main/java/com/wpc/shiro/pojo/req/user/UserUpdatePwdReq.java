package com.wpc.shiro.pojo.req.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 王鹏程
 * @Date: 2019/2/28 0028
 * @Time: 下午 10:00
 */
@Data
public class UserUpdatePwdReq {

    @ApiModelProperty(value = "用户新密码", required = true)
    @NotNull
    private String newPassword;

    @ApiModelProperty(value = "用户原密码", required = true)
    @NotNull
    private String password;

    @ApiModelProperty(value = "用户id", required = true)
    @NotNull
    private Integer userId;

}
