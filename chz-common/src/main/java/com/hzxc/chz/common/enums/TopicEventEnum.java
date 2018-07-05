package com.hzxc.chz.common.enums;

/**
 * create by chz on 2017/11/20
 */
public enum TopicEventEnum {
    LIKE(1,true),
    CANCEL_LIKE(1,false),
    FAVOR(2,true),
    CANCEL_FAVOR(2,false),
    WATCHED(3,true),
    ;

    private int type;
    private boolean value;

    TopicEventEnum(int type,boolean value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public static TopicEventEnum parseName(String name){
        for (TopicEventEnum e:TopicEventEnum.values()){
            if(e.name().equalsIgnoreCase(name)){
                return e;
            }
        }
        return null;
    }
}
