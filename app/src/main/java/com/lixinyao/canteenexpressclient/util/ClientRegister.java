package com.lixinyao.canteenexpressclient.util;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/17-20:36
 **/
public class ClientRegister {
    //获取前端传来的数据
    private String name;
    private String study_ID;
    private String tel;
    private String password;

    public ClientRegister(){}
    public ClientRegister(String name,String study_ID,String tel,String password){
        this.name=name;
        this.study_ID=study_ID;
        this.tel=tel;
        this.password=password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudy_ID(String study_ID) {
        this.study_ID = study_ID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public String getStudy_ID() {
        return study_ID;
    }

    public String getTel() {
        return tel;
    }

    public String getPassword() {
        return password;
    }
}
