package com.tondz.letstravel.Activity.User.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tondz.letstravel.Activity.ModuleAdapter.NewFeedListAdapter;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

public class NewFeedTabFragment extends Fragment {
    ListView lv_newfeeds;
    ViewGroup viewGroup;
    NewFeedListAdapter newFeedListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.new_feeds_tab_fragment,container,false);
        initView();
        newFeedListAdapter = new NewFeedListAdapter(getContext(),R.layout.custom_listview_newfeeds, StartActivity.database.getAllNews());
        lv_newfeeds.setAdapter(newFeedListAdapter);
        return viewGroup;
    }
    private void initView(){
        lv_newfeeds = viewGroup.findViewById(R.id.listview_newfeeds);
    }

}
