package com.wpc.shiro.service;

import com.wpc.shiro.bean.Group;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.exception.CustomException;
import com.wpc.shiro.mapper.GroupMapper;
import com.wpc.shiro.model.CommonCode;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.group.GroupReq;
import com.wpc.shiro.pojo.res.group.GroupRes;
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
public class GroupService {

    @Autowired
    private GroupMapper groupMapper;

    /**
     * 新增部门
     *
     * @param groupReq
     * @param userId
     * @return
     */
    public Results save(GroupReq groupReq, int userId) {
        Group group = new Group();
        BeanUtils.copyProperties(groupReq, group);
        group.setCreateBy(userId);
        groupMapper.insertSelective(group);
        return Results.resultSucc();
    }

    /**
     * 更新部门
     *
     * @param groupReq
     * @param userId
     * @return
     */
    public Results update(GroupReq groupReq, int userId) {
        Group group = groupMapper.selectByPrimaryKey(groupReq.getId());
        if (null == group) {
            CustomException.cast(CommonCode.GROUP_CODE_4001);
        }
        BeanUtils.copyProperties(groupReq, group);
        group.setUpdateBy(userId);
        groupMapper.updateByPrimaryKeySelective(group);
        return Results.resultSucc();
    }

    /**
     * 删除部门
     *
     * @param groupId
     * @return
     */
    public Results delete(Integer groupId) {
        List<Group> groupList = groupMapper.queryByParentId(groupId);
        if (null != groupList && groupList.size() > Constant.INT0){
            CustomException.cast(CommonCode.GROUP_CODE_4002);
        }
        groupMapper.deleteByPrimaryKey(groupId);
        return Results.resultSucc();
    }

    /**
     * 根据父id获取菜单列表
     *
     * @param parentGroupId
     * @return
     */
    public Results<List<GroupRes>> queryByParentId(Integer parentGroupId) {
        List<Group> groupList = groupMapper.queryByParentId(parentGroupId);
        List<GroupRes> groupResList = BeanMapper.mapList(groupList, GroupRes.class);
        return Results.resultSucc(groupResList);
    }

    /**
     * 获取部门
     *
     * @param groupId
     * @return
     */
    public Results<GroupRes> query(Integer groupId) {
        GroupRes groupRes = new GroupRes();
        Group group = groupMapper.selectByPrimaryKey(groupId);
        BeanUtils.copyProperties(group, groupRes);
        return Results.resultSucc(groupRes);
    }

    /**
     * 获取部门列表
     *
     * @param query
     * @return
     */
    public Results<PageUtils> list(Query query) {
        List<Group> list = groupMapper.list(query);
        int listTotal = groupMapper.listTotal(query);
        List<GroupRes> groupRes = BeanMapper.mapList(list, GroupRes.class);
        PageUtils pageUtils = new PageUtils(groupRes, listTotal, query.getLimit(), query.getPage());
        return Results.resultSucc(pageUtils);
    }
}
