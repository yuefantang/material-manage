package com.dongyu.company.common.constants;

/**
 * 数据修改状态枚举
 *
 * @author TYF
 * @date 2018/11/30
 * @since 1.0.0
 */
public enum OperationStateEnum {

    ADD(1, "新增"),
    UPDATE(2, "修改");
    private final int value;
    private final String desc;

    private OperationStateEnum(int value, String desc) {
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
