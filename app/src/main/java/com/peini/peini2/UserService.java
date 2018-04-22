package com.peini.peini2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2017/8/22.
 */

public class UserService {
    //config
    private static String url="jdbc:mysql://47.94.86.98:3306/peini_users";
    private static String user="root";
    private static String passwordSQL="YUsh0617";

    //login
    public static Integer login(User person){
        try{
            Connection conn = MySQLUtil.openConnection(url,user,passwordSQL);
            Statement sta = conn.createStatement();
            String sql = "select * from users;";
            ResultSet res = sta.executeQuery(sql);

            String name = person.getName();
            String password = person.getPassword();

            while (res.next()){
                if(res.getString(2).equals(name)) {
                    if(res.getString(6).equals(password)){
                        if(res.getString(5).equals("old")){
                            PersonData.userName = name;
                            PersonData.userRealName = res.getString(3);
                            PersonData.userMobile = res.getString(4);
                            res.close();
                            sta.close();
                            conn.close();
                            return 1;
                        }else{
                            PersonData.userName=name;
                            PersonData.userRealName = res.getString(3);
                            PersonData.userMobile = res.getString(4);
                            res.close();
                            sta.close();
                            conn.close();
                            return 2;
                        }
                    }else{
                        res.close();
                        sta.close();
                        conn.close();
                        return 0;
                    }
                }
            }

            res.close();
            sta.close();
            conn.close();
            return 0;

        }
        catch (SQLException e) {
            return 0;
        }

    }

    //register
    public static Boolean register(User person){
        try {
            Connection conn = MySQLUtil.openConnection(url,user,passwordSQL);
            Statement sta = conn.createStatement();
            String sql = "INSERT INTO `users` (`name`, `realname`, `mobile`, `type`, `password`) VALUES ('"+person.getName()+"', '"+person.getRealName()+"', '"+person.getMobile()+"', '"+person.getType()+"', '"+person.getPassword()+"');";
            sta.executeUpdate(sql);

            sta.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //showpair

    //formPair
    public static Boolean formPair(String oldName,String youngName){
        try{
            Connection conn = MySQLUtil.openConnection(url,user,passwordSQL);
            Statement sta = conn.createStatement();

            String sql = "INSERT INTO `peini_users`.`pairs` (`oldname`, `youngname`) VALUES ('"+oldName+"', '"+youngName+"');";

            sta.executeUpdate(sql);

            sta.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //show person data
    public static User showPerson(String userName){
        try {
            Connection conn = MySQLUtil.openConnection(url,user,passwordSQL);
            Statement sta = conn.createStatement();
            String sql = "SELECT * FROM peini_users.users where name = '"+userName+"';";
            ResultSet res = sta.executeQuery(sql);

            while (res.next()){
                return new User(res.getString(2),res.getString(3),res.getString(4));
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
