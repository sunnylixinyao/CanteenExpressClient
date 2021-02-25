package com.lixinyao.canteenexpressclient.util;

public enum DataResultError {

    M000000("{\"RECODE\":\"000000\",\"MSG\":\"SUCCESS\"}","000000"),//请求成功
    M000001("{\"RECODE\":\"000001\",\"MSG\":\"No such Person\"}","000001"),//请求成功，但是没有该账号
    M000002("{\"RECODE\":\"000002\",\"MSG\":\"update is wrong\"}","000002"),//时间更新出现错误
    M000003("{\"RECODE\":\"000003\",\"MSG\":\"don't get verifyCode\"}","000003"),//没有获取到验证码
    M000004("{\"RECODE\":\"000004\",\"MSG\":\"this accout has existed\"}","000004"),//账号已存在
    M000005("{\"RECODE\":\"000005\",\"MSG\":\"No Province data\"}","000005"),//没有省份信息
    M000006("{\"RECODE\":\"000006\",\"MSG\":\"No City data\"}","000006"),//没有城市信息
    M000007("{\"RECODE\":\"000007\",\"MSG\":\"No School data\"}","000007"),//没有学校信息
    M000008("{\"RECODE\":\"000008\",\"MSG\":\"No Friends\"}","000008"),//没有好友
    M000009("{\"RECODE\":\"000009\",\"MSG\":\"No such user\"}","000009"),//没有该用户
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
