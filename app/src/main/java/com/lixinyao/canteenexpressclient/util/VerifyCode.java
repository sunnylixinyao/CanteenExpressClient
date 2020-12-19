package com.lixinyao.canteenexpressclient.util;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/19-18:11
 **/
public class VerifyCode {
    private int code;
    public VerifyCode(){}
    public VerifyCode(int code){
        this.code=code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
