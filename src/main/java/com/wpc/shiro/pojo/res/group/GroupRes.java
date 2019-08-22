package com.wpc.shiro.pojo.res.group;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: 王鹏程
 * @Date: 2019/3/2 0002
 * @Time: 下午 3:32
 */
@Data
public class GroupRes {
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "部门父id")
    private Long parentId;

    @ApiModelProperty(value = "部门编号")
    private String groupNo;

    @ApiModelProperty(value = "部门名称")
    private String groupName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "标记")
    private String remark;
}
