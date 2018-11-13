package com.dongyu.company.common.constants;

/**
 * service通用的常量
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
public class Constants {

    //根据时间排序常量
    public static final String CREATE_TIME = "createTime";

    //用户删除常量
    public static final boolean USER_DELETED = true;
    //用户没删除常量
    public static final boolean USER_NOT_DELETED = false;

    //数字校验规则
    public static final String NUMBER_PATTERN = "^[0-9]*$";

    //密码规则
    public static final String PASSWORD_PATTERN = "^[0-9a-zA-Z]{6}$";
}
