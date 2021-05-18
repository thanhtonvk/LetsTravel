package com.tondz.letstravel.Activity.Admin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tondz.letstravel.Activity.ModuleAdapter.NewFeedListAdapter;
import com.tondz.letstravel.Model.CommentNews;
import com.tondz.letstravel.Model.News;
import com.tondz.letstravel.Model.Places;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsManagementActivity extends AppCompatActivity {


    ListView lv_news;
    AutoCompleteTextView edt_search;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_management);
        initView();
        NewFeedListAdapter listAdapter = new NewFeedListAdapter(this,R.layout.custom_listview_newfeeds, StartActivity.database.getAllNews());
        lv_news.setAdapter(listAdapter);
        edt_search.setAdapter(listAdapter);
        eventClick();
    }

    private void initView() {
        lv_news = findViewById(R.id.lv_admin_newsmanager);
        edt_search = findViewById(R.id.edt_newsmanager_search);
        btn_add = findViewById(R.id.btn_newsmanager_add);

    }

    private void eventClick() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UpdateNewsActivity.class));
            finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),MenuAdmin.class));
    }
}