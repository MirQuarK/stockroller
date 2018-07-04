package com.hzxc.chz.common.enums;

/**
 * create by chz on 2017/11/20
 */
public enum VideoStyle {
    HORIZONTAL(1),//横屏
    VERTICAL(2)//竖屏
    ;

    private int val;

    VideoStyle(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
