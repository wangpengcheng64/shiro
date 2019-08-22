package com.wpc.shiro.service;

import com.wpc.shiro.bean.User;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.exception.CustomException;
import com.wpc.shiro.mapper.UserMapper;
import com.wpc.shiro.model.CommonCode;
import com.wpc.shiro.model.Results;
import com.wpc.shiro.pojo.req.user.UserUpdatePwdReq;
import com.wpc.shiro.pojo.res.user.UserRes;
import com.wpc.shiro.utils.JwtUtil;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 王鹏程
 * @Date: 2019/2/24 0024
 * @Time: 下午 5:36
 */
@Service
public class LoginService {

  @Autowired
  private UserMapper userMapper;

  public Results login(String username, String password) {
    User user = userMapper.queryByUsername(username);
    if (null == user || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())){
      CustomException.cast(CommonCode.USER_CODE_1003);
    }
    String token = JwtUtil.sign(user.getUsername(), user.getSalt());
    return Results.resultSucc(token);
  }

  /**
   * 用户密码修改
   *
   * @param userUpdatePwdReq
   * @return
   */
  public Results updatePassword(UserUpdatePwdReq userUpdatePwdReq) {
    if (!userUpdatePwdReq.getPassword().equals(userUpdatePwdReq.getNewPassword())) {
      CustomException.cast(CommonCode.USER_CODE_1007);
    }
    User user = userMapper.selectByPrimaryKey(userUpdatePwdReq.getUserId());
    if (null == user) {
      CustomException.cast(CommonCode.USER_CODE_1005);
    }
    String password = new Sha256Hash(userUpdatePwdReq.getPassword(), user).toHex();
    if (!user.getPassword().equals(password)) {
      CustomException.cast(CommonCode.USER_CODE_1008);
    }
    user.setPassword(password);
    return userMapper.updateByPrimaryKeySelective(user) > Constant.INT0 ? Results.resultSucc() : Results.resultErr();
  }
}
