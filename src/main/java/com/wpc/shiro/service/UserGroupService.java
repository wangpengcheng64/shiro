package com.wpc.shiro.service;

import com.wpc.shiro.common.Constant;
import com.wpc.shiro.mapper.UserGroupMapper;
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
public class UserGroupService {

    @Autowired
    private UserGroupMapper userGroupMapper;

    /**
     * 新增或更新角色菜单关系
     * @param userId
     * @param groupIdList
     */
    public Results saveOrUpdate(Integer userId, List<Integer> groupIdList) {
        //先删除之前关系
        userGroupMapper.delete(userId);
        if (null == groupIdList || groupIdList.size() == Constant.INT0){
            return Results.resultSucc();
        }
        //新增关系
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("groupIdList", groupIdList);
        userGroupMapper.save(params);
        return Results.resultSucc();
    }

    /**
     * 根据角色id查询关系列表
     * @param userId
     * @return
     */
    public Results<List<Integer>> queryByUserId(Integer userId) {
        List<Integer> idList = userGroupMapper.queryGroupIdList(userId);
        return Results.resultSucc(idList);
    }
}
