package com.tondz.letstravel.Controller;

import android.app.Activity;
import android.widget.Toast;

import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.StartActivity;

import java.util.List;

public class Register extends Activity {
    public static boolean checkUniqueUsername(String username){
        boolean check = false;
        for (Account account: StartActivity.database.getAllAccounts()
             ) {
            if(account.getUsername().equals(username)){
                check = true;
            }
        }
        return check;
    }
    public static boolean checkPassword(String password,String pre_password){
        if(password.equals(pre_password)) return true;
        else return false;
    }
}
