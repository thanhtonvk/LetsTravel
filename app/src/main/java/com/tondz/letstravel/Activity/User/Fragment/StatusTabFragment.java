package com.tondz.letstravel.Activity.User.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tondz.letstravel.Activity.User.ModuleAdapter.StatusListAdapter;
import com.tondz.letstravel.Model.Status;
import com.tondz.letstravel.R;

import java.util.ArrayList;

public class StatusTabFragment extends Fragment {
    ListView lv_status;
    ViewGroup viewGroup;
    StatusListAdapter statusListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.status_tab_fragment, container, false);
        initView();
        setLv_newfeeds();
        return viewGroup;
    }

    private void initView() {
        lv_status = viewGroup.findViewById(R.id.listview_status);
    }

    public void setLv_newfeeds() {
        ArrayList<Status> statuses = new ArrayList<>();
        //statuses.add(new Status(R.drawable.pic,"Đỗ Thành Tôn","Ngày hôm nay vui quá là vui"));
        statusListAdapter = new StatusListAdapter(getContext(),statuses );
        lv_status.setAdapter(statusListAdapter);
    }
}
