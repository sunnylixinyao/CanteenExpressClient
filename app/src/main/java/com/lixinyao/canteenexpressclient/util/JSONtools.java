package com.lixinyao.canteenexpressclient.util;

import java.util.ArrayList;

public class JSONtools {
    //将JSON格式的数据转换为ArrayList数组
    public static ArrayList<String> jsonToArray(String postData) {
        ArrayList<String> data = new ArrayList<String>();
        String temp;
        for (int i = 0; i < postData.length(); i++) {
            if (postData.charAt(i) == '"') {
                for (int j = i + 1; j < postData.length(); j++) {
                    if (postData.charAt(j) == '"') {
                        temp = postData.substring(i + 1, j);
                        data.add(temp);
                        i = j;
                        j = postData.length();
                    }
                }

            }
        }
        return data;
    }

}
