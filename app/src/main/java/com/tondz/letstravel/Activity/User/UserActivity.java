package com.tondz.letstravel.Activity.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.google.android.material.tabs.TabLayout;
import com.tondz.letstravel.Activity.LoginAdapter;
import com.tondz.letstravel.Activity.User.Adapter.UserAdapter;
import com.tondz.letstravel.R;

public class UserActivity extends AppCompatActivity {

    public static TabLayout tabLayout;
    ViewPager viewPager;
    MultiAutoCompleteTextView multiSearch;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        setTabLayout();

    }

    private void initView() {
        tabLayout = findViewById(R.id.tablayout_ActivityUser);
        viewPager = findViewById(R.id.viewpager_ActivityUser);
        multiSearch = findViewById(R.id.autoComplete_AcitivyUser_search);
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        userAdapter = new UserAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(userAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.getTabAt(0).setCustomView(R.layout.view_home);
        tabLayout.getTabAt(1).setCustomView(R.layout.view_map);
        tabLayout.getTabAt(2).setCustomView(R.layout.view_status);
        tabLayout.getTabAt(3).setCustomView(R.layout.view_menu);
    }

    @Override
    public void onBackPressed() {

    }
}