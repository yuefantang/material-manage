package com.dongyu.company.common.constants;

/**
 * 其它收费开单类型枚举
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
public enum ChargeTypeEnum {

    MOULD_TYPE(1, "模具开单"),
    TEMPLATE_TYPE(2, "样板开单"),
    OTHER_TYPE(3, "其它开单");

    private final int value;
    private final String desc;

    private ChargeTypeEnum(int value, String desc) {
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
