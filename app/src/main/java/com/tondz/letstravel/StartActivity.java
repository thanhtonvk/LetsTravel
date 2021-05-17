package com.tondz.letstravel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tondz.letstravel.Activity.LoginActivity;
import com.tondz.letstravel.Database.Database;

public class StartActivity extends Activity {

    public static Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        database = new Database(this);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        super.onCreate(savedInstanceState);
    }
}
