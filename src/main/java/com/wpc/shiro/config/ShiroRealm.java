package com.wpc.shiro.config;

import com.wpc.shiro.bean.Menu;
import com.wpc.shiro.bean.User;
import com.wpc.shiro.common.Constant;
import com.wpc.shiro.mapper.MenuMapper;
import com.wpc.shiro.mapper.UserMapper;
import com.wpc.shiro.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Mr.Li
 * @create 2018-07-12 15:23
 * @desc
 **/
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 必须重写此方法，不然Shiro会报错
     * UsernamePasswordToken upt = new UsernamePasswordToken(username,password);
     * Subject subject = SecurityUtils.getSubject();
     * subject.login(upt);
     * 登陆操作执行上部分代码会执行此方法，若返回true，则会继续调用下面doGetAuthenticationInfo方法
     * 进行登入确认，并且返回SimpleAuthenticationInfo对象。
     * 因为当前是通过jwt生成的token来校验，因此登陆不执行上部分代码，而是执行LoginController的login方法
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        //boolean flag = token instanceof JwtToken;
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)  throws AuthenticationException{
        User user = (User) principals.getPrimaryPrincipal();
        Integer userId = user.getId();
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.ADMIN){
            List<Menu> menuList = menuMapper.list(new HashMap<>());
            permsList = new ArrayList<>(menuList.size());
            for(Menu menu : menuList){
                permsList.add(menu.getPermission());
            }
        }else{
            permsList = menuMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permsSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException{
        String token = (String) auth.getCredentials();
        // 先将token与redis中的进行比较，若查不到或者不匹配则返回失败，重新登陆
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new IncorrectCredentialsException ("token无效");
        }
        User user = userMapper.queryByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("用户不存在!");
        }
        if (!JwtUtil.verify(token, username, user.getSalt())) {
            //TODO 如果token已过期，则于redis中的比较
            // 1、若果redis中还存在，则重新生成token，并将redis中覆盖
            // token = JwtUtil.sign(user.getUsername(), user.getSalt());
            // 2、若没过期则将redis中的token重置过期时间
            throw new ExpiredCredentialsException("token已过期");
        }
        //principal 主角, 相当于把登陆用户信息放入session中
        //credentials 凭证：与token的密码进行比较，如果相同，则成功，否则认证失败
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token, getName());
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader("token", token);
        return info;
    }
}
