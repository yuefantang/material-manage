package com.dongyu.company.common.constants;

/**
 * 模具采购种类枚举
 *
 * @author TYF
 * @date 2019/1/2
 * @since 1.0.0
 */
public enum ProcurementTypeEnum {
    MOULD(1, "模具"), TEST_RACK(2, "测试架");
    private final int value;
    private final String desc;

    private ProcurementTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getValue(int index) {
        for (ProcurementTypeEnum typeEnum : ProcurementTypeEnum.values()) {
            if (typeEnum.getValue() == index) {
                return typeEnum.getDesc();
            }
        }
        return null;
    }

}
