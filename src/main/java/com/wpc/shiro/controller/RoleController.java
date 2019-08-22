package com.wpc.shiro.controller;

import com.wpc.shiro.annotation.SysLog;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.role.RoleReq;
import com.wpc.shiro.pojo.res.role.RoleRes;
import com.wpc.shiro.service.RoleService;
import com.wpc.shiro.utils.PageUtils;
import com.wpc.shiro.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: 王鹏程
 * @Date: 2019/2/27 0027
 * @Time: 下午 11:26
 */
@Api(tags = "角色管理", description = "RoleController")
@Log4j2
@RestController
@RequestMapping("/sys")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;

    @SysLog("新增角色")
    @ApiOperation(value = "新增角色")
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public Results save(@RequestBody @Validated RoleReq roleReq) {
        log.info("新增角色, request param:{}", roleReq);
        return roleService.save(roleReq, getUserId());
    }

    @SysLog("更新角色")
    @ApiOperation(value = "更新角色")
    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    public Results update(@RequestBody @Validated RoleReq roleReq) {
        log.info("更新角色, request param:{}", roleReq);
        if (null == roleReq.getId()) {
            return Results.resultErr("角色id不能为空!");
        }
        return roleService.update(roleReq, getUserId());
    }

    @SysLog("删除角色")
    @ApiOperation(value = "删除角色")
    @RequestMapping(value = "/role/delete", method = RequestMethod.DELETE)
    public Results update(@RequestBody Integer[] roleIds) {
        log.info("删除角色, request param:{}", roleIds);
        return roleService.deleteBatch(roleIds);
    }

    @SysLog("获取角色")
    @ApiOperation(value = "获取角色")
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public Results<RoleRes> query(@PathVariable("roleId") Integer roleId) {
        log.info("获取角色, request param:{}", roleId);
        return roleService.query(roleId);
    }

    @SysLog("获取角色列表")
    @ApiOperation(value = "获取角色列表")
    @RequestMapping(value = "/role/list", method = RequestMethod.GET)
    public Results<PageUtils> list(@RequestParam Map<String, Object> map) {
        log.info("获取角色列表, request param:{}", map);
        paramCheck(map);
        Query query = new Query(map);
        return roleService.list(query);
    }
}
