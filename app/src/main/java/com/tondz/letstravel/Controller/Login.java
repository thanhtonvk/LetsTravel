package com.tondz.letstravel.Controller;

import com.tondz.letstravel.Model.Account;

import java.util.List;

public class Login {
    public static int checkLogin(String username, String password, List<Account>accounts){
        int check = 0;
        for (Account account:accounts
             ) {
            if(username.equals(account.getUsername())&&password.equals(account.getPassword())){
                check = 1;
                //client
            }
            if(username.equals(account.getUsername())&&password.equals(account.getPassword())&&account.isPermission()){
                check = 2;
                //admin
            }
        }
        return check;
    }
}
