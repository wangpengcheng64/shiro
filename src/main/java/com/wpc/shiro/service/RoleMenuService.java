package com.wpc.shiro.service;

import com.wpc.shiro.common.Constant;
import com.wpc.shiro.mapper.RoleMenuMapper;
import com.wpc.shiro.model.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 王鹏程
 * @Date: 2019/3/2 0002
 * @Time: 上午 10:57
 */
@Service
public class RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 新增或更新角色菜单关系
     * @param roleId
     * @param menuIdList
     */
    public Results saveOrUpdate(Integer roleId, List<Integer> menuIdList) {
        //先删除之前关系
        roleMenuMapper.delete(roleId);
        if (null == menuIdList || menuIdList.size() == Constant.INT0){
            return Results.resultSucc();
        }
        //新增关系
        Map<String, Object> params = new HashMap<>();
        params.put("roleId", roleId);
        params.put("menuIdList", menuIdList);
        roleMenuMapper.save(params);
        return Results.resultSucc();
    }

    /**
     * 根据角色id查询关系列表
     * @param roleId
     * @return
     */
    public Results<List<Integer>> queryByRoleId(Integer roleId) {
        List<Integer> idList = roleMenuMapper.queryMenuIdList(roleId);
        return Results.resultSucc(idList);
    }
}
