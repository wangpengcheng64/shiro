package com.wpc.shiro.mapper;

import com.wpc.shiro.bean.UserGroup;

import java.util.List;
import java.util.Map;

public interface UserGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGroup record);

    int insertSelective(UserGroup record);

    UserGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGroup record);

    int updateByPrimaryKey(UserGroup record);

    void delete(Integer userId);

    void save(Map<String, Object> params);

    List<Integer> queryGroupIdList(Integer roleId);
}