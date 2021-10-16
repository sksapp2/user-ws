package com.infc.ms.user.enums;

public enum UserStatusEnum {
    ACTIVE("ACTIVE"),INACTIVE("INACTIVE"),BLOCK("BLOCK");
    private String userStatus;
    UserStatusEnum(String userStatus){
        this.userStatus=userStatus;
    }
    public String getUserStatus(){
        return this.userStatus;
    }

}
