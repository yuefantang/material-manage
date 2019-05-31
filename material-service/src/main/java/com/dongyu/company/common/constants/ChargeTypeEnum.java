package com.dongyu.company.common.constants;

/**
 * 收费种类枚举
 *
 * @author TYF
 * @date 2019/1/12
 * @since 1.0.0
 */
public enum ChargeTypeEnum {

    MOULD_TYPE(1, "模具收费"), TEMPLATE_TYPE(2, "样板收费"), OTHER_TYPE(3, "其它收费"), ORDER_TYPE(4, "订单收费"), TEST_RACK_TYPE(5, "测试架收费");

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

    public static String getValue(int index) {
        for (ChargeTypeEnum typeEnum : ChargeTypeEnum.values()) {
            if (typeEnum.getValue() == index) {
                return typeEnum.getDesc();
            }
        }
        return null;
    }
}
