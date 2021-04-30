package com.tondz.letstravel.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tondz.letstravel.Controller.Register;
import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterTabFragment extends Fragment {
    List<Account> accounts;
    Button btn_register;
    ViewGroup viewGroup;
    EditText editText_username, editText_password, editText_prepassword, editText_phonenumber, editText_email;
    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);
        initView();
        Account account = new Account();
        try {
            accounts = account.readFile(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        alertDialog();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Register.registerAccount(editText_username.getText().toString(), editText_password.getText().toString(), editText_prepassword.getText().toString(), editText_email.getText().toString(), editText_phonenumber.getText().toString(), accounts)) {
                    case 1:
                        builder.setMessage("Tên tài khoản đã tồn tại, vui lòng nhập tài khoản khác");
                        builder.show();
                        break;
                    case 2:
                        builder.setMessage("Số điện thoại đã tồn tại, vui lòng nhập số điện thoại khác");
                        builder.show();
                        break;
                    case 3:
                        builder.setMessage("Email đã tồn tại, vui lòng nhập email khác");
                        builder.show();
                        break;
                    case 4:
                        builder.setMessage("Mật khẩu nhập lại không khớp, vui lòng kiểm tra lại");
                        builder.show();
                        break;
                    default:
                        builder.setMessage("Tại tài khoản thành công");
                        builder.show();
                        accounts.add(new Account(editText_username.getText().toString(), editText_password.getText().toString(), editText_email.getText().toString(), editText_phonenumber.getText().toString()));
                        account.writeFile(accounts);
                        break;
                }
            }
        });
        return viewGroup;
    }

    private void initView() {
        accounts = new ArrayList<>();
        btn_register = viewGroup.findViewById(R.id.btn_register_createaccount);
        editText_username = viewGroup.findViewById(R.id.edt_register_username);
        editText_password = viewGroup.findViewById(R.id.edt_register_password);
        editText_prepassword = viewGroup.findViewById(R.id.edt_register_prepassword);
        editText_phonenumber = viewGroup.findViewById(R.id.edt_register_phonenumber);
        editText_email = viewGroup.findViewById(R.id.edt_register_email);
    }

    private void alertDialog() {
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

    }
}
