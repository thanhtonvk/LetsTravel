package com.tondz.letstravel.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.google.android.libraries.places.widget.Autocomplete;
import com.tondz.letstravel.Activity.ModuleAdapter.StatusAdapter;
import com.tondz.letstravel.Model.Status;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

public class StatusManagerActivity extends AppCompatActivity {


    ListView lv_status;
    AutoCompleteTextView edt_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_manager);
        initView();
        StatusAdapter statusAdapter = new StatusAdapter(getApplicationContext(),R.layout.custom_listview_status, StartActivity.database.getAllStatus());
        lv_status.setAdapter(statusAdapter);
        edt_search.setAdapter(statusAdapter);

    }
    private void initView(){
        lv_status = findViewById(R.id.lv_admin_statusmanager);
        edt_search = findViewById(R.id.edt_statusmanager_search);
    }
}