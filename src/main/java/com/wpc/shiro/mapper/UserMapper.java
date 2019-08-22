package com.wpc.shiro.mapper;

import com.wpc.shiro.bean.User;
import com.wpc.shiro.model.Results;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User queryByUsername(String username);

    List<User> list(Map<String, Object> map);

    int listTotal(Map<String, Object> map);

    void deleteBatch(Long[] userIds);
}