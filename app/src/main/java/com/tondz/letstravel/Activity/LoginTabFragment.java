package com.tondz.letstravel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    List<Account> accounts;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);
        initView();
        Account account = new Account();
        accounts = new ArrayList<>();
        try {
            accounts = account.readFile(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Login.checkLogin(editText_username.getText().toString(),editText_password.getText().toString(),accounts)==0){
//                    Toast.makeText(getActivity(),"Tài khoản hoặc mật khẩu không chính xác",Toast.LENGTH_SHORT).show();
//                }
//                else if(Login.checkLogin(editText_username.getText().toString(),editText_password.getText().toString(),accounts)==1){
//                    Toast.makeText(getActivity(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
//                }
//                else if(Login.checkLogin(editText_username.getText().toString(),editText_password.getText().toString(),accounts)==2){
//                    Toast.makeText(getActivity(),"Bạn là quản trị viên",Toast.LENGTH_SHORT).show();
//                }
                if(editText_password.getText().toString().equals("")&&editText_username.getText().toString().equals("")){
                    startActivity(new Intent(getActivity(), UserActivity.class));
                    onStop();
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
}
