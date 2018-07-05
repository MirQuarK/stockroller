package com.hzxc.chz.common.enums;

public class Enumes {
    public enum LoginTypeEnum {
        ALL(0, "ALL"),  // 所有类型
        APP(1, "APP"),  // app登陆
        XCX(2, "XCX");  // 小程序登陆

        LoginTypeEnum (int val, String msg){
            this.val = val;
            this.msg = msg;
        }

        public int val() {
            return val;
        }

        public String msg() {
            return msg;
        }

        private int val;
        private String msg;

        public static LoginTypeEnum parseType(String msg){
            for (LoginTypeEnum e:LoginTypeEnum.values()){
                if(e.msg().equalsIgnoreCase(msg)){
                    return e;
                }
            }
            return null;
        }

        public static LoginTypeEnum parseType(int val){
            for (LoginTypeEnum e:LoginTypeEnum.values()){
                if(e.val() == val){
                    return e;
                }
            }
            return null;
        }
    }

    public enum UserTypeEnum {
        ROOT(0, "ROOT"),
        ADMIN(1, "ADMIN"),
        USER(2, "USER");

        UserTypeEnum (int val, String msg){
            this.val = val;
            this.msg = msg;
        }

        public int val() {
            return val;
        }

        public String msg() {
            return msg;
        }

        private int val;
        private String msg;

        public static UserTypeEnum parseType(String msg){
            for (UserTypeEnum e:UserTypeEnum.values()){
                if(e.msg().equalsIgnoreCase(msg)){
                    return e;
                }
            }
            return null;
        }

        public static UserTypeEnum parseType(int val){
            for (UserTypeEnum e:UserTypeEnum.values()){
                if(e.val() == val){
                    return e;
                }
            }
            return null;
        }
    }

    public enum PrizeCountEnum {
        ONCE(0, "ONCE"),  // 每日一次奖励
        MANY(1, "MANY");  // 每次完成都有奖励

        PrizeCountEnum (int val, String msg){
            this.val = val;
            this.msg = msg;
        }

        public int val() {
            return val;
        }

        public String msg() {
            return msg;
        }

        private int val;
        private String msg;

        public static PrizeCountEnum parseType(String msg){
            for (PrizeCountEnum e: PrizeCountEnum.values()){
                if(e.msg().equalsIgnoreCase(msg)){
                    return e;
                }
            }
            return null;
        }

        public static PrizeCountEnum parseType(int val){
            for (PrizeCountEnum e: PrizeCountEnum.values()){
                if(e.val() == val){
                    return e;
                }
            }
            return null;
        }
    }
}
