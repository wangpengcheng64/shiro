package com.wpc.shiro.mapper;

import com.wpc.shiro.bean.UserRole;

import java.util.List;
import java.util.Map;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    void delete(Integer userId);

    void save(Map<String, Object> params);

    List<Integer> queryRoleIdList(Integer userId);
}