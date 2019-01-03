package com.dongyu.company.common.constants;

/**
 * 是否收费和收费开单状态枚举
 *
 * @author TYF
 * @date 2019/1/2
 * @since 1.0.0
 */
public enum CurrencyEunm {
    NO(0, "否"),
    YES(1, "是");
    private final int value;
    private final String desc;

    private CurrencyEunm(int value, String desc) {
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
