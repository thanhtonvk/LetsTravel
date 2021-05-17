package com.tondz.letstravel.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tondz.letstravel.Activity.Admin.MenuAdmin;
import com.tondz.letstravel.Activity.Admin.NewsManagementActivity;
import com.tondz.letstravel.Activity.User.UserActivity;

import com.tondz.letstravel.Controller.Login;
import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginTabFragment extends Fragment {
    Button btn_login_login;
    EditText editText_username;
    EditText editText_password;
    ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);
        initView();
        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Login.checkLogin(editText_username.getText().toString(),editText_password.getText().toString())){
                    case 0:
                        dialogLogin("Username and password incorrect!!");
                        break;
                    case 1:
                        dialogLogin("Password incorrect");
                        break;
                    case 2:
                        dialogLogin("Username incorrect");
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(),UserActivity.class));
                        Toast.makeText(getContext(),"WELCOME "+Login.account.getFullname(),Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(),MenuAdmin.class));
                        break;
                }
            }
        });

        return viewGroup;
    }
    private void initView(){
        btn_login_login = viewGroup.findViewById(R.id.btn_login_login);
        editText_username = viewGroup.findViewById(R.id.edt_login_username);
        editText_password = viewGroup.findViewById(R.id.edt_login_password);
    }
    private void dialogLogin(String content){
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setMessage(content);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
