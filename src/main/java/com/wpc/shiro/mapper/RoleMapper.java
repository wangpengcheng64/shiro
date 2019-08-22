package com.wpc.shiro.mapper;

import com.wpc.shiro.bean.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    void deleteBatch(Integer[] roleIds);

    List<Role> list(Map query);

    int listTotal(Map query);
}