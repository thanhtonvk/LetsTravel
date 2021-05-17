package com.tondz.letstravel.Controller;

import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.StartActivity;

public class Login {
    public static Account account;
    public static int checkLogin(String username,String password){
        int check = 0;
        for (Account ac: StartActivity.database.getAllAccounts()
             ) {
            if(ac.getUsername().equals(username)&&!ac.getPassword().equals(password)){
                check = 1;
            }
            else if (!ac.getUsername().equals(username)&&ac.getPassword().equals(password)){
                check = 2;
            }
            else if(ac.getUsername().equals(username)&&ac.getPassword().equals(password)){
                if(ac.getUsername().equals("admin")){
                    check = 4; account = ac;
                }
                else {
                    check = 3;
                    account = ac;
                }
            }
        }
        return check;
    }
}
