package com.tondz.letstravel.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {
    private String username;
    private String password;
    private String email;
    private String phonenumber;
    private boolean permission;

    public Account() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        Account.file = file;
    }

    public Account(String username, String password, String email, String phonenumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.permission = false;
    }

    public Account(String username, String password, String email, String phonenumber, boolean permission) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
        this.permission = permission;
    }

    @Override
    public String toString() {
        return username + ";" + password + ";" + email + ";" + phonenumber + ";" + permission;
    }

    static File file = new File("login.csv");

    public void writeFile(List<Account> accounts) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                for (Account account : accounts
                ) {
                    outputStreamWriter.write(account.toString() + "\n");
                }
                outputStreamWriter.close();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Account> readFile(List<Account> accounts) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String str = "";
            while ((str = bufferedReader.readLine()) != null) {
                String[] arr = str.split(";");
                accounts.add(new Account(arr[0], arr[1], arr[2], arr[3], Boolean.parseBoolean(arr[4])));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
