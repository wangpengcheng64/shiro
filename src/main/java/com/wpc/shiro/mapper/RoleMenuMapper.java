package com.wpc.shiro.mapper;

import com.wpc.shiro.bean.RoleMenu;

import java.util.List;
import java.util.Map;

public interface RoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    RoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);

    int update(Object id);

    List<Integer> queryMenuIdList(Integer roleId);

    void delete(Integer roleId);

    void save(Map<String, Object> params);
}