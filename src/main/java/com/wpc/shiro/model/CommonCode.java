package com.wpc.shiro.model;

import lombok.ToString;

@ToString
public enum CommonCode implements ResultCode {
    /**
     * 通用
     */
    SUCCESS(true, 200, "操作成功！"),
    FAIL(false, 404, "操作失败！"),
    PARAM_EXISTS_NULL(false, 404, "参数存在空值，请重新检查再提交！"),
    SERVER_ERROR(false, 999, "抱歉，系统繁忙，请稍后重试！"),
    NOT_AUTHORIZED(false, 401, "抱歉，您没有权限访问！"),
    TOKEN_ERROR(false, 401, "抱歉，token错误！"),
    ILLEGAL_CHARACTER(false, 401, "非法字符！"),
    PAGE_ERROR(false, 401, "参数page不能为空！"),
    LIMIT_ERROR(false, 401, "参数limit不能为空！"),

    /**
     * token验证
     */
    INVALID_TOKEN(false, 401, "invalidToken"),
    CONTAIN_ILLEGAL_CHAR(false, 401, "包含非法字符！"),

    /**
     * 用户信息验证
     */
    USER_CODE_1001(false, 1001, "用户名不能为空！"),
    USER_CODE_1002(false, 1002, "密码不能为空！"),
    USER_CODE_1003(false, 1003, "账号或密码不正确！"),
    USER_CODE_1004(false, 1004, "账号已被锁定,请联系管理员！"),
    USER_CODE_1005(false, 1005, "用户未注册"),
    USER_CODE_1006(false, 1006, "用户已注册，请直接登陆"),
    USER_CODE_1007(false, 1007, "亲，两次输入的密码不一致"),
    USER_CODE_1008(false, 1008, "亲，旧密码不正确"),
    USER_CODE_1009(false, 1009, "密码不正确,请重新输入"),

    /**
     * 角色信息验证
     */
    ROLE_CODE_2001(false, 2001, "角色信息查询失败！"),

    /**
     * 菜单信息验证
     */
    MENU_CODE_3001(false, 3001, "菜单URL不能为空!"),
    MENU_CODE_3002(false, 3002, "上级菜单只能为目录类型!"),
    MENU_CODE_3003(false, 3003, "上级菜单只能为菜单类型!"),
    MENU_CODE_3004(false, 3004, "菜单信息查询失败!"),
    MENU_CODE_3005(false, 3005, "请先删除子菜单或按钮!"),

    /**
     * 部门信息验证
     */
    GROUP_CODE_4001(false, 4001, "部门信息查询失败！"),
    GROUP_CODE_4002(false, 4002, "请先删除子部门！"),

    /**
     * 验证相关
     */
    AUTH_CODE_10001(false, 11001, "短信验证码发送失败,请重新下发"),
    AUTH_CODE_10002(false, 11002, "短信验证码不正确,请重新输入"),
    AUTH_CODE_10003(false, 11003, "短信验证码已过期，请重新下发"),
    AUTH_CODE_10004(false, 11004, "亲，还未登录或登录已失效，请退出后重新登录"),
    AUTH_CODE_10005(false, 11005, "验证码已使用，请重新下发"),
    AUTH_CODE_10006(false, 11006, "业务类型不正确"),;

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
