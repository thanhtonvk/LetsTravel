package com.tondz.letstravel.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
    Button btn_register,btn_choose_image;
    ViewGroup viewGroup;
    EditText editText_username, editText_password, editText_prepassword, editText_phonenumber, editText_email,editText_dateofbirth;
    AlertDialog.Builder builder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);
        initView();
        eventOnclick();
        return viewGroup;
    }

    private void initView() {
        accounts = new ArrayList<>();
        btn_register = viewGroup.findViewById(R.id.btn_register_createaccount);
        editText_username = viewGroup.findViewById(R.id.edt_register_username);
        editText_password = viewGroup.findViewById(R.id.edt_register_password);
        editText_prepassword = viewGroup.findViewById(R.id.edt_register_prepassword);
        editText_phonenumber = viewGroup.findViewById(R.id.edt_register_phonenumber);
        editText_dateofbirth = viewGroup.findViewById(R.id.edt_register_dateofbirth);
        editText_email = viewGroup.findViewById(R.id.edt_register_email);
        btn_choose_image = viewGroup.findViewById(R.id.btn_choose_avatar);
    }

    private void eventOnclick(){
       btn_choose_image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               custom_Dialog();
           }
       });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void custom_Dialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog_choose_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
