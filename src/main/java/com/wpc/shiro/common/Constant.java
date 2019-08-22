package com.wpc.shiro.common;

public class Constant {

    public static final int INT0 = 0;
    public static final int ADMIN = 1;
    public static final int SALT = 20;
    public static final String PAGE = "page";
    public static final String LIMIT = "limit";

    /**
     * 菜单类型
     *
     * @author zyd
     * @email 727600298@qq.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
