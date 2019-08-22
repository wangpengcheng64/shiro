package com.wpc.shiro.controller;

import com.wpc.shiro.annotation.SysLog;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.user.UserReq;
import com.wpc.shiro.pojo.res.user.UserRes;
import com.wpc.shiro.service.UserService;
import com.wpc.shiro.utils.PageUtils;
import com.wpc.shiro.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: 王鹏程
 * @Date: 2019/2/24 0024
 * @Time: 下午 5:29
 */
@Api(tags = "用户管理", description = "UserController")
@Log4j2
@RestController
@RequestMapping("/sys")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @SysLog("新增用户")
    @ApiOperation(value = "新增用户")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @RequiresPermissions("user:save")
    public Results save(@Validated @RequestBody UserReq userReq) {
        log.info("新增用户, request param:{}", userReq.getUsername());
        if (StringUtils.isEmpty(userReq.getPassword())) {
            return Results.resultErr("用户密码不能为空!");
        }
        return userService.save(userReq, getUserId());
    }

    @SysLog("用户修改")
    @ApiOperation(value = "用户信息修改")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @RequiresPermissions("user:update")
    public Results update(@Validated @RequestBody UserReq userReq) {
        log.info("用户信息修改, request param:{}", userReq);
        if (null == userReq.getId()) {
            return Results.resultErr("用户id不能为空!");
        }
        return userService.update(userReq, getUserId());
    }

    @ApiOperation(value = "获取当前登入用户信息")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Results<UserRes> query() {
        log.info("获取当前登入用户信息");
        return Results.resultSucc(getUser());
    }

    @SysLog("通过id获取用户信息")
    @ApiOperation(value = "通过id获取用户信息")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user:query")
    public Results<UserRes> query(@PathVariable("id") Integer id) {
        log.info("通过id获取用户信息, request param:{}", id);
        return userService.query(id);
    }

    @SysLog("获取用户列表")
    @ApiOperation(value = "获取用户列表")
    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    @RequiresPermissions("user:list")
    public Results<PageUtils> list(@RequestParam Map<String, Object> map) {
        log.info("获取用户列表, request param:{}", map);
        paramCheck(map);
        Query query = new Query(map);
        return userService.list(query);
    }

    @SysLog("用户删除")
    @ApiOperation(value = "用户删除")
    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    @RequiresPermissions("user:delete")
    public Results delete(@RequestBody Long[] userIds) {
        log.info("用户删除, request param:{}", userIds);
        if (ArrayUtils.contains(userIds, 1L)) {
            return Results.resultErr("系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return Results.resultErr("当前用户不能删除");
        }
        return userService.delete(userIds);
    }
}
