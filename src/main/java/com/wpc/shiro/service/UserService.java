package com.wpc.shiro.service;

import com.wpc.shiro.bean.User;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.exception.CustomException;
import com.wpc.shiro.mapper.UserMapper;
import com.wpc.shiro.model.CommonCode;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.user.UserReq;
import com.wpc.shiro.pojo.res.user.UserRes;
import com.wpc.shiro.utils.BeanMapper;
import com.wpc.shiro.utils.PageUtils;
import com.wpc.shiro.utils.Query;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: 王鹏程
 * @Date: 2019/2/24 0024
 * @Time: 下午 5:36
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserGroupService userGroupService;

    /**
     * 新增用户
     *
     * @param userReq
     * @return
     */
    public Results save(UserReq userReq, Integer userId) {
        User user = new User();
        BeanUtils.copyProperties(userReq, user);
        String salt = RandomStringUtils.randomAlphanumeric(Constant.SALT);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        user.setCreateBy(userId);
        userMapper.insertSelective(user);
        userGroupService.saveOrUpdate(user.getId(), userReq.getGroupIdList());
        userRoleService.saveOrUpdate(user.getId(), userReq.getRoleIdList());
        return Results.resultSucc();
    }

    /**
     * 用户信息修改
     *
     * @param userReq
     * @return
     */
    public Results update(UserReq userReq, Integer userId) {
        User user = userMapper.selectByPrimaryKey(userReq.getId());
        if (null == user) {
            CustomException.cast(CommonCode.USER_CODE_1005);
        }
        BeanUtils.copyProperties(userReq, user);
        user.setUpdateBy(userId);
        userMapper.updateByPrimaryKeySelective(user);
        userGroupService.saveOrUpdate(userReq.getId(), userReq.getGroupIdList());
        userRoleService.saveOrUpdate(userReq.getId(), userReq.getRoleIdList());
        return Results.resultSucc();
    }

    /**
     * 通过id获取用户信息
     *
     * @param id
     * @return
     */
    public Results<UserRes> query(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (null == user) {
            CustomException.cast(CommonCode.USER_CODE_1005);
        }
        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(user, userRes);
        List<Integer> roleIdList = userRoleService.queryByUserId(id).getResult();
        List<Integer> groupIdList = userGroupService.queryByUserId(id).getResult();
        userRes.setRoleIdList(roleIdList);
        userRes.setGroupIdList(groupIdList);
        return Results.resultSucc(userRes);
    }

    /**
     * 获取用户列表
     *
     * @param query
     * @return
     */
    public Results<PageUtils> list(Query query) {
        List<User> list = userMapper.list(query);
        int listTotal = userMapper.listTotal(query);
        List<UserRes> userRes = BeanMapper.mapList(list, UserRes.class);
        PageUtils pageUtils = new PageUtils(userRes, listTotal, query.getLimit(), query.getPage());
        return Results.resultSucc(pageUtils);
    }

    /**
     * 用户删除
     *
     * @param userIds
     * @return
     */
    public Results delete(Long[] userIds) {
        userMapper.deleteBatch(userIds);
        return Results.resultSucc();
    }
}
