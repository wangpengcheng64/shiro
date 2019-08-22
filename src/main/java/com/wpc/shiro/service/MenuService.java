package com.wpc.shiro.service;

import com.wpc.shiro.bean.Menu;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.exception.CustomException;
import com.wpc.shiro.mapper.MenuMapper;
import com.wpc.shiro.model.CommonCode;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.menu.MenuReq;
import com.wpc.shiro.pojo.res.menu.MenuRes;
import com.wpc.shiro.utils.BeanMapper;
import com.wpc.shiro.utils.PageUtils;
import com.wpc.shiro.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 王鹏程
 * @Date: 2019/2/27 0027
 * @Time: 下午 11:29
 */
@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 新增菜单
     *
     * @param menuReq
     * @param userId
     * @return
     */
    public Results save(MenuReq menuReq, int userId) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuReq, menu);
        menu.setCreateBy(userId);
        menuMapper.insertSelective(menu);
        return Results.resultSucc();
    }

    /**
     * 更新菜单
     *
     * @param menuReq
     * @param userId
     * @return
     */
    public Results update(MenuReq menuReq, int userId) {
        Menu menu = menuMapper.selectByPrimaryKey(menuReq.getId());
        if (null == menu) {
            CustomException.cast(CommonCode.MENU_CODE_3004);
        }
        BeanUtils.copyProperties(menuReq, menu);
        menu.setUpdateBy(userId);
        menuMapper.updateByPrimaryKeySelective(menu);
        return Results.resultSucc();
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    public Results delete(Integer menuId) {
        List<Menu> menuList = menuMapper.queryByParentId(menuId);
        if (null != menuList && menuList.size() > Constant.INT0){
            CustomException.cast(CommonCode.MENU_CODE_3005);
        }
        menuMapper.deleteByPrimaryKey(menuId);
        return Results.resultSucc();
    }

    /**
     * 根据父id获取菜单列表
     *
     * @param parentMenuId
     * @return
     */
    public Results<List<MenuRes>> queryByParentId(Integer parentMenuId) {
        List<Menu> menuList = menuMapper.queryByParentId(parentMenuId);
        List<MenuRes> menuResList = BeanMapper.mapList(menuList, MenuRes.class);
        return Results.resultSucc(menuResList);
    }

    /**
     * 获取菜单
     *
     * @param menuId
     * @return
     */
    public Results<MenuRes> query(Integer menuId) {
        MenuRes menuRes = new MenuRes();
        Menu menu = menuMapper.selectByPrimaryKey(menuId);
        BeanUtils.copyProperties(menu, menuRes);
        return Results.resultSucc(menuRes);
    }

    /**
     * 获取菜单列表
     *
     * @param query
     * @return
     */
    public Results<PageUtils> list(Query query) {
        List<Menu> list = menuMapper.list(query);
        int listTotal = menuMapper.listTotal(query);
        List<MenuRes> menuRes = BeanMapper.mapList(list, MenuRes.class);
        PageUtils pageUtils = new PageUtils(menuRes, listTotal, query.getLimit(), query.getPage());
        return Results.resultSucc(pageUtils);
    }
}
