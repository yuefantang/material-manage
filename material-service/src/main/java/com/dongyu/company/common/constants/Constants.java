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

    //只能输入正整数校验规则
    public static final String NUMBER_PATTERN = "^[0-9]*$";

    //只能输入整数或小数的正则表达式校验规则
    public static final String NUMBER_POINT_PATTERN = "^[0-9]+([.]{1}[0-9]+){0,1}$";

    //只能输入乘法算式的正则表达式校验规则（针对尺寸）（23.34*89）
    public static final String SIZE_PATTERN = "^[0-9]+([.]{1}[0-9]+){0,1}(\\*[0-9]+([.]{1}[0-9]+)){0,1}$";

    //密码规则
    public static final String PASSWORD_PATTERN = "^[0-9a-zA-Z]{6,}$";

    //生成xls最大允许的行数
    public static final int MAX_XLS_TOTAL_ROW = 1000000;

    //生成xls最大允许的单sheet行数
    public static final int MAX_XLS_SHEET_ROW = 60000;
}
