package com.peini.peini2;

import android.app.Activity;

/**
 * Created by Administrator on 2017/11/3.
 */

public class Match {
    public static int doMatch(String str){
        String [] index = {"个人中心","配对","退出"};
        for (int i = 0; i < index.length;i++){
            if(str.indexOf(index[i]) >= 0){
                return i;
            }
        }
        return -1;
    }
}
