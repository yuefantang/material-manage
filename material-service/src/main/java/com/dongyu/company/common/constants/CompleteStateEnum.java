package com.dongyu.company.common.constants;

/**
 * 下单是否完成枚举
 *
 * @author TYF
 * @date 2018/12/5
 * @since 1.0.0
 */
public enum CompleteStateEnum {

    COMPLETE(0, "未完成"),
    UNCOMPLETE(1, "完成");
    private final int value;
    private final String desc;

    private CompleteStateEnum(int value, String desc) {
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
