package com.infc.ms.user.enums;

public enum KeysStoreEnum {
    FILE_SYSTEM("FILE_SYSTEM"),DB("DB"),PLAIN_TEXT("PLAIN_TEXT");

    private String keyValues;
    private KeysStoreEnum(String keyValues){
        this.keyValues=keyValues;

    }
    public String getKeyStore(){
        return  this.keyValues;
    }
}
