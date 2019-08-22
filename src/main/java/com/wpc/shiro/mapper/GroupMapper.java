package com.wpc.shiro.mapper;

import com.wpc.shiro.bean.Group;

import java.util.List;
import java.util.Map;

public interface GroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);

    void deleteBatch(Integer[] groupIds);

    List<Group> list(Map query);

    int listTotal(Map query);

    List<Group> queryByParentId(Integer parentGroupId);
}