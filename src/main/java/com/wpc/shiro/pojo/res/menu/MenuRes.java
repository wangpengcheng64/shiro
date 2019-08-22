package com.wpc.shiro.pojo.res.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 王鹏程
 * @Date: 2019/3/2 0002
 * @Time: 下午 3:19
 */
@Data
public class MenuRes {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "菜单父id")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单权限")
    private String permission;

    @ApiModelProperty(value = "资源类型(0:目录 1:菜单 2:按钮)")
    private Integer resourceType;

    @ApiModelProperty(value = "是否展示(0:显示 1:隐藏)")
    private Integer isShow;

    @ApiModelProperty(value = "菜单url")
    private String url;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    private String description;
}
