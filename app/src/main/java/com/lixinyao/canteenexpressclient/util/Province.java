package com.lixinyao.canteenexpressclient.util;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/22-0:37
 **/
public class Province {
    private int id;
    private String name;

    public Province(){

    }
    public Province(int id, String name){
        this.id=id;
        this.name=name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
