package com.lixinyao.canteenexpressclient.util;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/22-0:45
 **/
public class School {
    private int id;
    private int City_id;
    private int School_id;
    private String School_name;
    private String School_erea;

    public School(){

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCity_id(int city_id) {
        City_id = city_id;
    }

    public void setSchool_erea(String school_erea) {
        School_erea = school_erea;
    }

    public void setSchool_id(int school_id) {
        School_id = school_id;
    }

    public void setSchool_name(String school_name) {
        School_name = school_name;
    }

    public int getCity_id() {
        return City_id;
    }

    public int getId() {
        return id;
    }

    public int getSchool_id() {
        return School_id;
    }

    public String getSchool_erea() {
        return School_erea;
    }

    public String getSchool_name() {
        return School_name;
    }
}
