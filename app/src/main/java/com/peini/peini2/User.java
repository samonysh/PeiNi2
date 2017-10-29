package com.peini.peini2;

/**
 * Created by Administrator on 2017/8/22.
 */

public class User {
    private String name;
    private String realName;
    private String mobile;
    private String type;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String realName, String mobile, String type, String password) {
        this.name = name;
        this.realName = realName;
        this.mobile = mobile;
        this.type = type;
        this.password = password;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String realName, String mobile) {
        this.name = name;
        this.realName = realName;
        this.mobile = mobile;
    }
}
