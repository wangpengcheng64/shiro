package com.wpc.shiro.service;

import com.wpc.shiro.bean.Role;
import com.wpc.shiro.exception.CustomException;
import com.wpc.shiro.mapper.RoleMapper;
import com.wpc.shiro.model.CommonCode;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.role.RoleReq;
import com.wpc.shiro.pojo.res.role.RoleRes;
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
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 新增角色
     * @param roleReq
     * @param userId
     * @return
     */
    public Results save(RoleReq roleReq, int userId) {
        Role role = new Role();
        BeanUtils.copyProperties(roleReq, role);
        role.setCreateBy(userId);
        roleMapper.insertSelective(role);
        roleMenuService.saveOrUpdate(role.getId(), roleReq.getMenuIdList());
        return Results.resultSucc();
    }

    /**
     * 更新角色
     * @param roleReq
     * @param userId
     * @return
     */
    public Results update(RoleReq roleReq, int userId) {
        Role role = roleMapper.selectByPrimaryKey(roleReq.getId());
        if (null == role) {
            CustomException.cast(CommonCode.ROLE_CODE_2001);
        }
        BeanUtils.copyProperties(roleReq, role);
        role.setUpdateBy(userId);
        roleMapper.updateByPrimaryKeySelective(role);
        roleMenuService.saveOrUpdate(role.getId(), roleReq.getMenuIdList());
        return Results.resultSucc();
    }

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    public Results deleteBatch(Integer[] roleIds) {
        roleMapper.deleteBatch(roleIds);
        return Results.resultSucc();
    }

    /**
     * 获取角色
     * @param roleId
     * @return
     */
    public Results<RoleRes> query(Integer roleId) {
        RoleRes roleRes = new RoleRes();
        Role role = roleMapper.selectByPrimaryKey(roleId);
        BeanUtils.copyProperties(role, roleRes);
        List<Integer> ids =  roleMenuService.queryByRoleId(roleId).getResult();
        roleRes.setMenuIdList(ids);
        return Results.resultSucc(roleRes);
    }

    /**
     * 获取角色列表
     * @param query
     * @return
     */
    public Results<PageUtils> list(Query query) {
        List<Role> list = roleMapper.list(query);
        int listTotal = roleMapper.listTotal(query);
        List<RoleRes> roleRes = BeanMapper.mapList(list, RoleRes.class);
        PageUtils pageUtils = new PageUtils(roleRes, listTotal, query.getLimit(), query.getPage());
        return Results.resultSucc(pageUtils);
    }
}
