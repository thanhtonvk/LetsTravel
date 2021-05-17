package com.tondz.letstravel.Activity.User.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tondz.letstravel.Activity.User.Fragment.MapTabFragment;
import com.tondz.letstravel.Activity.User.Fragment.MenuTabFragment;
import com.tondz.letstravel.Activity.User.Fragment.NewFeedTabFragment;
import com.tondz.letstravel.Activity.User.Fragment.StatusTabFragment;

public class UserAdapter extends FragmentPagerAdapter {

    String[] list = {"New Feeds", "Map","Status","Menu"};
    private Context context;
    private int totalTab;

    public UserAdapter(@NonNull FragmentManager fm, Context context, int totalTab) {
        super(fm);
        this.context = context;
        this.totalTab = totalTab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewFeedTabFragment newFeedTabFragment = new NewFeedTabFragment();
                return newFeedTabFragment;
            case 1:
                MapTabFragment mapTabFragment = new MapTabFragment();
                return mapTabFragment;
            case 2:
                StatusTabFragment statusTabFragment = new StatusTabFragment();
                return statusTabFragment;
            case 3:
                MenuTabFragment menuTabFragment = new MenuTabFragment();
                return menuTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list[position];
    }
}
