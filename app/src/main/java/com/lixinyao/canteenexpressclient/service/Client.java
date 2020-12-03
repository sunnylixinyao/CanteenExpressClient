package com.lixinyao.canteenexpressclient.service;

public class Client {
    private String ID;
    private String Password;

    public Client(){}
    public Client(String ID,String Password){
        this.ID=ID;
        this.Password=Password;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public void setPassword(String password) {
        Password = password;
    }


    public String getID() {
        return ID;
    }

    public String getPassword() {
        return Password;
    }

}
