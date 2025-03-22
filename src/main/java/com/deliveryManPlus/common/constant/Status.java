package com.deliveryManPlus.common.constant;

public enum Status {
    USE,DELETED,;

    public static Status of(String value){
        if(value == null){
            return null;
        }
        if(value.equals("Y")){
            return USE;
        }else if(value.equals("N")){
            return DELETED;
        }
        return null;
    }
}
