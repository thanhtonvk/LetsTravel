package com.tondz.letstravel.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tondz.letstravel.R;

public class MenuAdmin extends AppCompatActivity {

    Button btn_newfeed,btn_user,btn_status,btn_places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        initView();
        eventClick();
    }
    private void eventClick(){
        btn_newfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewsManagementActivity.class));
            }
        });
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserMangerActivity.class));
            }
        });
        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PlacesManagerActivity.class));
            }
        });
        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),StatusManagerActivity.class));
            }
        });

    }
    private void initView(){
        btn_newfeed = findViewById(R.id.btn_admin_newsmanagement);
        btn_user = findViewById(R.id.btn_admin_usermanagement);
        btn_status =findViewById(R.id.btn_admin_statusmanagement);
        btn_places = findViewById(R.id.btn_admin_placesmanagement);
    }
}