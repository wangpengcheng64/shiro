package com.wpc.shiro.pojo.req.user;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import lombok.Data;

import java.util.List;

@Data
public class UserReq {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户名称", required = true)
    @NotNull
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "电话", required = true)
    @NotNull
    private String phone;

    @ApiModelProperty(value = "邮箱", required = true)
    @NotNull
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "角色列表", required = true)
    @NotNull
    private List<Integer> roleIdList;

    @ApiModelProperty(value = "部门列表", required = true)
    @NotNull
    private List<Integer> groupIdList;
}