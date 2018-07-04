package com.hzxc.chz.common.enums;

/**
 * create by chz on 2018/1/25
 */
public enum CurrencyType {
    RMB(1, 1),//分
    GOLD(2, 100),;//100金币兑换1分
    private int type;
    private int rate;

    CurrencyType(int type, int rate) {
        this.type = type;
        this.rate = rate;
    }

    public int getType() {
        return type;
    }

    public int getRate() {
        return rate;
    }

    public static CurrencyType parse(String typeName) {
        for (CurrencyType currencyType : values()) {
            if (currencyType.name().equalsIgnoreCase(typeName))
                return currencyType;
        }
        return null;
    }
}
