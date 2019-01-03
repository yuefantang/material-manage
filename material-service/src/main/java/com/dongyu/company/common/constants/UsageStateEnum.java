package com.dongyu.company.common.constants;

/**
 * 模具使用状态
 *
 * @author TYF
 * @date 2019/1/2
 * @since 1.0.0
 */
public enum UsageStateEnum {

    ORDER(1, "订购"),
    USE(2, "使用"),
    DIE_REPAIR(3, "修模"),
    SCRAP(4, "报废"),
    RETURN_CUSTOME(5, "退回客户");
    private final int value;
    private final String desc;

    private UsageStateEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
