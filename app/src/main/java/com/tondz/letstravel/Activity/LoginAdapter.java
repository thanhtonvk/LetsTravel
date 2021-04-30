package com.tondz.letstravel.Activity;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {

    String []list = {"Đăng nhập","Tạo tài khoản"};
    private Context context;
    private int totalTab;
    public  LoginAdapter(FragmentManager fragmentManager,Context context,int totalTab){
        super(fragmentManager);
        this.context = context;
        this.totalTab = totalTab;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;
            case 1:
                RegisterTabFragment registerTabFragment = new RegisterTabFragment();
                return registerTabFragment;
            default:return  null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list[position];
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
