package com.tondz.letstravel.Model;

public class Account {
    private String username,password,fullname,dateofbirth,email,phonenumber;
    private byte[]avatar;
    private String permission;

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public Account(String username, String password, String fullname, String dateofbirth, String email, String phonenumber, byte[] avatar) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.dateofbirth = dateofbirth;
        this.email = email;
        this.phonenumber = phonenumber;
        this.avatar = avatar;
        permission ="user";
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Account(String username, String password, String fullname, String dateofbirth, String email, String phonenumber, byte[] avatar, String permission) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.dateofbirth = dateofbirth;
        this.email = email;        //    String username, String password, String fullname, String dateofbirth, String email, String phonenumber, byte[] avatar, String permission
        this.phonenumber = phonenumber;
        this.avatar = avatar;
        this.permission = permission;
    }
}
