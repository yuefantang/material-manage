package com.dongyu.company.common.constants;

/**
 * 是否删除枚举
 *
 * @author TYF
 * @date 2018/11/28
 * @since 1.0.0
 */
public enum DeletedEnum {
    UNDELETED(0, "未删除"),
    DELETED(1, "删除");
    private final int value;
    private final String desc;

    private DeletedEnum(int value, String desc) {
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
