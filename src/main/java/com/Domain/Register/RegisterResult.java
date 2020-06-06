package com.Domain.Register;

import lombok.Data;

@Data
public class RegisterResult {
    private boolean result;
    private String str;

    private RegisterResult(boolean result,String str){
        this.result= result;
        this.str =str;
    }

    public static final RegisterResult SUCCESS = new RegisterResult(true, null);
    public static final RegisterResult DUPLICATE = new RegisterResult(false, "用户名已存在");
    public static final RegisterResult ERROR = new RegisterResult(false, "发生未知错误");

}
