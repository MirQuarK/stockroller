package com.hzxc.chz.common.enums;

/**
 * create by chz on 2018/1/23
 */
public enum TaskState {
    LOCK(0),
    DOING(1),
    COMPLETE(2),
    FINISH(3),
    ;

    private int state;
    private TaskState(int state){
        this.state = state;
    }
}
