package com.tondz.letstravel.Activity.User.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.tondz.letstravel.Activity.LoginActivity;
import com.tondz.letstravel.Activity.User.Adapter.UserAdapter;
import com.tondz.letstravel.Activity.User.ProfileActivity;
import com.tondz.letstravel.Activity.WebViewActivity;
import com.tondz.letstravel.Controller.Login;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.util.List;

public class MenuTabFragment extends Fragment {
    ViewGroup viewGroup;
    LinearLayout view_profile;
    ImageView avatar;
    TextView name;
    Button btn_playgame;
    Button media;
    Button btn_logout;
    public static String link ="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.menu_tab_fragment, container, false);
        init();
        view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
        avatar.setImageBitmap(BitmapFactory.decodeByteArray(Login.account.getAvatar(),0,Login.account.getAvatar().length));
        name.setText(Login.account.getFullname());
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChooseMedia();
            }
        });
        btn_playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChooseGame();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return viewGroup;
    }

    private void init() {
        view_profile = viewGroup.findViewById(R.id.btn_open_profile);
        avatar = viewGroup.findViewById(R.id.img_tabmenu_avatar);
        name = viewGroup.findViewById(R.id.edt_menutab_name);
        btn_playgame = viewGroup.findViewById(R.id.btn_playgame);
        media = viewGroup.findViewById(R.id.btn_youtube);
        btn_logout = viewGroup.findViewById(R.id.btn_logout);
    }
    private void dialogChooseMedia(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_choose_media);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn_youtube = dialog.findViewById(R.id.btn_openyoutube);
        Button btn_soundcloud = dialog.findViewById(R.id.btn_opensoundcloud);
        btn_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = "https://m.youtube.com/";
                startActivity(new Intent(getActivity(),WebViewActivity.class));
            }
        });
        btn_soundcloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = "https://soundcloud.com/";
                startActivity(new Intent(getActivity(),WebViewActivity.class));
            }
        });
        dialog.show();
    }
    private void dialogChooseGame(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_choose_games);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn_2048 = dialog.findViewById(R.id.btn_play2048);
        Button btn_flap = dialog.findViewById(R.id.btn_playflappybird);
        Button btn_slit = dialog.findViewById(R.id.btn_playslitherio);
        btn_2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link="https://play2048.co/";
                startActivity(new Intent(getActivity(),WebViewActivity.class));
            }
        });
        btn_flap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link ="https://flappybird.io/";
                startActivity(new Intent(getActivity(),WebViewActivity.class));
            }
        });
        btn_slit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = "http://slither.io/";
                startActivity(new Intent(getActivity(),WebViewActivity.class));
            }
        });
        dialog.show();
    }
}
