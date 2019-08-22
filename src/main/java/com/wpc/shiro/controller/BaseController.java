package com.wpc.shiro.controller;

import com.wpc.shiro.bean.User;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.exception.CustomException;
import com.wpc.shiro.model.CommonCode;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.res.user.UserRes;
import com.wpc.shiro.service.UserGroupService;
import com.wpc.shiro.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @Author: 王鹏程
 * @Date: 2019/2/24 0024
 * @Time: 下午 5:29
 */
@Slf4j
public class BaseController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserGroupService userGroupService;

    /**
     * 获取当前登陆用户
     * @return
     */
    protected UserRes getUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(user, userRes);
        List<Integer> roleIdList = userRoleService.queryByUserId(user.getId()).getResult();
        List<Integer> groupIdList = userGroupService.queryByUserId(user.getId()).getResult();
        userRes.setRoleIdList(roleIdList);
        userRes.setGroupIdList(groupIdList);
        return userRes;
    }

    /**
     * 获取当前登陆用户id
     * @return
     */
    protected Integer getUserId() {
        Integer id = getUser().getId();
        return id;
    }

    /**
     * page,limit参数验证
     * @param map
     */
    protected void paramCheck(Map<String, Object> map){
        if (null == map.get(Constant.PAGE)) {
            CustomException.cast(CommonCode.PAGE_ERROR);
        }
        if (null == map.get(Constant.LIMIT)) {
            CustomException.cast(CommonCode.LIMIT_ERROR);
        }
    }

}
