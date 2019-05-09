package com.dongyu.company.common.constants;

/**
 * 开单类型枚举
 *
 * @author TYF
 * @date 2018/12/6
 * @since 1.0.0
 */
public enum BillingTypeEnum {
    PAYMENT_TYPE(1, "货款开单"),
    OTHER_CHARGES_TYPE(2, "其它收费开单");
    private final int value;
    private final String desc;

    private BillingTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    // 普通方法
    public static String getValue(int index) {
        for (BillingTypeEnum c : BillingTypeEnum.values()) {
            if (c.getValue() == index) {
                return c.desc;
            }
        }
        return null;
    }
}
