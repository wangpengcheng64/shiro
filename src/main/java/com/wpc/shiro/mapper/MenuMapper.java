package com.wpc.shiro.mapper;

import com.wpc.shiro.bean.Menu;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    void deleteBatch(Integer[] menuIds);

    List<Menu> list(Map query);

    int listTotal(Map query);

    List<Menu> queryByParentId(Integer parantMenuId);

    List<String> queryAllPerms(Integer userId);
}