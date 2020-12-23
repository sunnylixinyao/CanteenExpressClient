package com.lixinyao.canteenexpressclient.util;

public enum DataResultError {

    M000000("{\"RECODE\":\"000000\",\"MSG\":\"SUCCESS\"}","000000"),//请求成功
    M000001("{\"RECODE\":\"000001\",\"MSG\":\"No such Person\"}","000001"),//请求成功，但是没有该账号
    M000002("{\"RECODE\":\"000002\",\"MSG\":\"update is wrong\"}","000002"),//时间更新出现错误
    M000003("{\"RECODE\":\"000003\",\"MSG\":\"don't get verifyCode\"}","000003"),//没有获取到验证码
    M000004("{\"RECODE\":\"000004\",\"MSG\":\"this accout has existed\"}","000004"),//账号已存在
    M999999("{\"RECODE\":\"999999\",\"MSG\":\"system error\"}","999999");//系统错误

    private String MSG;
    private String CODE;

    private DataResultError(String MSG, String CODE) {
        this.MSG = MSG;
        this.CODE = CODE;
    }

    public String getMSG() {
        return MSG;
    }
    public String getCODE() {
        return CODE;
    }
}
