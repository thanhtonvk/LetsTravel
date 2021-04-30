package com.tondz.letstravel.Controller;

import android.app.Activity;
import android.widget.Toast;

import com.tondz.letstravel.Model.Account;

import java.util.List;

public class Register extends Activity {
    static boolean checkUniqueUsername(String username, List<Account> accounts) {
        boolean check = false;
        for (Account account : accounts
        ) {
            if (username.equals(account.getUsername())) check = true;
        }
        return check;
    }

    static boolean checkUniquePhoneNumber(String phoneNumber, List<Account> accounts) {
        boolean check = false;
        for (Account account : accounts
        ) {
            if (phoneNumber.equals(account.getPhonenumber())) check = true;
        }
        return check;
    }

    static boolean checkUniqueEmail(String email, List<Account> accounts) {
        boolean check = false;
        for (Account account : accounts
        ) {
            if (email.equals(account.getEmail())) check = true;
        }
        return check;
    }

    public static int registerAccount(String username, String password, String pre_password, String email, String phonenumber, List<Account> accounts) {
        int check = 0;

        if (checkUniqueUsername(username, accounts)) {
            check = 1;
            //Username exist
        }
        if (checkUniquePhoneNumber(phonenumber, accounts)) {
            check = 2;
            //Phonenumber exist
        }
        if (checkUniqueEmail(email, accounts)) {
            check = 3;
            //email exist;
        }
        if (!password.equals(pre_password)) {
            check = 4;
            //password doesn't same

        }
        return check;
    }
}
