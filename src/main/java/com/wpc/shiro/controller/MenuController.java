package com.wpc.shiro.controller;

import com.wpc.shiro.annotation.SysLog;
import com.wpc.shiro.bean.Menu;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.exception.CustomException;
import com.wpc.shiro.model.CommonCode;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.menu.MenuReq;
import com.wpc.shiro.pojo.res.menu.MenuRes;
import com.wpc.shiro.service.MenuService;
import com.wpc.shiro.utils.PageUtils;
import com.wpc.shiro.utils.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
@Api(tags = "菜单管理", description = "MenuController")
@Log4j2
@RestController
@RequestMapping("/sys")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @SysLog("新增菜单")
    @ApiOperation(value = "新增菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public Results save(@RequestBody @Validated MenuReq menuReq) {
        log.info("新增菜单, request param:{}", menuReq);
        verifyForm(menuReq);
        return menuService.save(menuReq, getUserId());
    }

    @SysLog("更新菜单")
    @ApiOperation(value = "更新菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.PUT)
    public Results update(@RequestBody @Validated MenuReq menuReq) {
        log.info("更新菜单, request param:{}", menuReq);
        if (null == menuReq.getId()) {
            return Results.resultErr("菜单id不能为空!");
        }
        verifyForm(menuReq);
        return menuService.update(menuReq, getUserId());
    }

    @SysLog("删除菜单")
    @ApiOperation(value = "删除菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.DELETE)
    public Results update(@RequestBody Integer menuId) {
        log.info("删除菜单, request param:{}", menuId);
        return menuService.delete(menuId);
    }

    @SysLog("获取菜单")
    @ApiOperation(value = "获取菜单")
    @RequestMapping(value = "/menu/{menuId}", method = RequestMethod.GET)
    public Results<MenuRes> query(@PathVariable("menuId") Integer menuId) {
        log.info("获取菜单, request param:{}", menuId);
        return menuService.query(menuId);
    }

    @SysLog("根据父id获取菜单列表")
    @ApiOperation(value = "根据父id获取菜单列表")
    @RequestMapping(value = "/menu/list/{menuId}", method = RequestMethod.GET)
    public Results<List<MenuRes>> queryByParentId(@PathVariable("menuId") Integer menuId) {
        log.info("根据父id获取菜单列表, request param:{}", menuId);
        return menuService.queryByParentId(menuId);
    }

    @SysLog("获取菜单列表")
    @ApiOperation(value = "获取菜单列表")
    @RequestMapping(value = "/menu/list", method = RequestMethod.GET)
    public Results<PageUtils> list(@RequestParam Map<String, Object> map) {
        log.info("获取菜单列表, request param:{}", map);
        paramCheck(map);
        Query query = new Query(map);
        return menuService.list(query);
    }

    private void verifyForm(MenuReq menu) {
        //菜单url不能为空
        if (menu.getResourceType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                CustomException.cast(CommonCode.MENU_CODE_3001);
            }
        }
        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            MenuRes menuRes = query(menu.getParentId()).getResult();
            parentType = menuRes.getResourceType();
        }
        //目录、菜单
        if (menu.getResourceType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getResourceType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                CustomException.cast(CommonCode.MENU_CODE_3002);
            }
            return;
        }
        //按钮
        if (menu.getResourceType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                CustomException.cast(CommonCode.MENU_CODE_3003);
            }
            return;
        }
    }
}
