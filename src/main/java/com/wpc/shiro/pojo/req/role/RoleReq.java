package com.wpc.shiro.pojo.req.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: 王鹏程
 * @Date: 2019/3/2 0002
 * @Time: 上午 10:27
 */
@Data
public class RoleReq {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "角色名称", required = true)
    @NotNull
    private String roleName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "角色id列表", required = true)
    @NotNull
    private List<Integer> menuIdList;

}
