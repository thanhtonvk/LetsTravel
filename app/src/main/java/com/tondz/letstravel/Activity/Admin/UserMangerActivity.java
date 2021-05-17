package com.tondz.letstravel.Activity.Admin;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.tondz.letstravel.Activity.ModuleAdapter.UsersAdapter;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

public class UserMangerActivity extends AppCompatActivity {

    AutoCompleteTextView edt_search;
    ListView lv_users;
    UsersAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manger);
        initView();
        userAdapter = new UsersAdapter(this, R.layout.custom_listview_usersinfo, StartActivity.database.getAllAccounts());
        lv_users.setAdapter(userAdapter);
        edt_search.setAdapter(userAdapter);
    }
    private void initView() {
        edt_search = findViewById(R.id.edt_usersmanager_search);
        lv_users = findViewById(R.id.lv_admin_usersmanager);
    }
}