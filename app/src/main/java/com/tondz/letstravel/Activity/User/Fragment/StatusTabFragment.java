package com.tondz.letstravel.Activity.User.Fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.tondz.letstravel.Activity.ModuleAdapter.StatusAdapter;
import com.tondz.letstravel.Activity.User.UpdateStatusActivity;
import com.tondz.letstravel.Controller.Login;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

public class StatusTabFragment extends Fragment {
    ListView lv_status;
    ViewGroup viewGroup;
    ImageView avatar;
    Button btn_checkin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.status_tab_fragment, container, false);
        initView();
        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), UpdateStatusActivity.class));
            }
        });
        StatusAdapter statusAdapter = new StatusAdapter(getContext(),R.layout.custom_listview_status, StartActivity.database.getAllStatus());
        lv_status.setAdapter(statusAdapter);
        avatar.setImageBitmap(BitmapFactory.decodeByteArray(Login.account.getAvatar(),0,Login.account.getAvatar().length));
        return viewGroup;
    }

    private void initView() {
        lv_status = viewGroup.findViewById(R.id.lv_user_liststatus);
        avatar = viewGroup.findViewById(R.id.img_user_status_avatar);
        btn_checkin = viewGroup.findViewById(R.id.btn_user_status_checking);
    }
}
