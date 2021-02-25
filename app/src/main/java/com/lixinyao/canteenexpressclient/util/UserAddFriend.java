package com.lixinyao.canteenexpressclient.util;

/**
 * @author lixinyao
 * @create 2021-02-2021/2/25-15:04
 **/
public class UserAddFriend {
    private String UserName;
    private String Friend_Name;
    private String Friend_Study_Id;

    public UserAddFriend(){}

    public UserAddFriend(String UserName,String Friend_Name,String Friend_Study_Id){
        this.UserName=UserName;
        this.Friend_Name=Friend_Name;
        this.Friend_Study_Id=Friend_Study_Id;
    }

    public void setFriend_Name(String friend_Name) {
        Friend_Name = friend_Name;
    }

    public void setFriend_Study_Id(String friend_Study_Id) {
        Friend_Study_Id = friend_Study_Id;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFriend_Name() {
        return Friend_Name;
    }

    public String getFriend_Study_Id() {
        return Friend_Study_Id;
    }

    public String getUserName() {
        return UserName;
    }
}
