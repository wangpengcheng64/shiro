package com.wpc.shiro.controller;

import com.wpc.shiro.annotation.SysLog;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.group.GroupReq;
import com.wpc.shiro.pojo.res.group.GroupRes;
import com.wpc.shiro.service.GroupService;
import com.wpc.shiro.utils.PageUtils;
import com.wpc.shiro.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: 王鹏程
 * @Date: 2019/2/27 0027
 * @Time: 下午 11:27
 */
@Api(tags = "部门管理", description = "GroupController")
@Log4j2
@RestController
@RequestMapping("/sys")
public class GroupController extends BaseController{

    @Autowired
    private GroupService groupService;

    @SysLog("新增部门")
    @ApiOperation(value = "新增部门")
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public Results save(@RequestBody @Validated GroupReq groupReq) {
        log.info("新增部门, request param:{}", groupReq);
        return groupService.save(groupReq, getUserId());
    }

    @SysLog("更新部门")
    @ApiOperation(value = "更新部门")
    @RequestMapping(value = "/group", method = RequestMethod.PUT)
    public Results update(@RequestBody @Validated GroupReq groupReq) {
        log.info("更新部门, request param:{}", groupReq);
        if (null == groupReq.getId()) {
            return Results.resultErr("部门id不能为空!");
        }
        return groupService.update(groupReq, getUserId());
    }

    @SysLog("删除部门")
    @ApiOperation(value = "删除部门")
    @RequestMapping(value = "/group", method = RequestMethod.DELETE)
    public Results delete(@RequestBody Integer groupId) {
        log.info("删除部门, request param:{}", groupId);
        return groupService.delete(groupId);
    }

    @SysLog("获取部门")
    @ApiOperation(value = "获取部门")
    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public Results<GroupRes> query(@PathVariable("groupId") Integer groupId) {
        log.info("获取角色, request param:{}", groupId);
        return groupService.query(groupId);
    }

    @SysLog("根据父id获取部门列表")
    @ApiOperation(value = "根据父id获取部门列表")
    @RequestMapping(value = "/group/list/{groupId}", method = RequestMethod.GET)
    public Results<List<GroupRes>> queryByParentId(@PathVariable("groupId") Integer groupId) {
        log.info("根据父id获取部门列表, request param:{}", groupId);
        return groupService.queryByParentId(groupId);
    }

    @SysLog("获取部门列表")
    @ApiOperation(value = "获取部门列表")
    @RequestMapping(value = "/group/list", method = RequestMethod.GET)
    public Results<PageUtils> list(@RequestParam Map<String, Object> map) {
        log.info("获取部门列表, request param:{}", map);
        paramCheck(map);
        Query query = new Query(map);
        return groupService.list(query);
    }
}
