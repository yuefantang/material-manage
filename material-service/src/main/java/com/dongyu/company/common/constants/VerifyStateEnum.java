package com.dongyu.company.common.constants;

/**
 * 核实状态枚举
 *
 * @author TYF
 * @date 2019/1/21
 * @since 1.0.0
 */
public enum VerifyStateEnum {

    VERIFY(1, "已核实"),
    UNVERIFY(0, "未核实");
    private final int value;
    private final String desc;

    private VerifyStateEnum(int value, String desc) {
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
