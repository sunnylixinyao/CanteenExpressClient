package com.lixinyao.canteenexpressclient.util;

public class Client {
    private String IDorTel;
    private String Password;

    public Client(){}
    public Client(String ID,String Password){
        this.IDorTel=ID;
        this.Password=Password;
    }

    public void setID(String ID) {
        this.IDorTel = ID;
    }


    public void setPassword(String password) {
        Password = password;
    }


    public String getID() {
        return IDorTel;
    }

    public String getPassword() {
        return Password;
    }

}
