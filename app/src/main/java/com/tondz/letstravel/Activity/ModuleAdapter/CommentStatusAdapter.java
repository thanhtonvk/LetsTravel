package com.tondz.letstravel.Activity.ModuleAdapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.Model.CommentNews;
import com.tondz.letstravel.Model.CommentStatus;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.util.List;

public class CommentStatusAdapter extends BaseAdapter {
    List<CommentStatus> commentStatusList;
    Context context;
    LayoutInflater inflater;

    public CommentStatusAdapter(Context context, List<CommentStatus>commentStatusList){
        this.context = context;

        this.commentStatusList = commentStatusList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return commentStatusList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_listview_comment_news,null);
        ImageView img = convertView.findViewById(R.id.img_customlistview_comment_avatar);
        TextView tv_name = convertView.findViewById(R.id.tv_customlistview_comment_name);
        TextView tv_content = convertView.findViewById(R.id.tv_customlistView_comment_content);
        CommentStatus commentStatus = commentStatusList.get(position);
        Account account = getUser(commentStatus.getUsername());
        img.setImageBitmap(BitmapFactory.decodeByteArray(account.getAvatar(),0,account.getAvatar().length));
        tv_name.setText(account.getFullname());
        tv_content.setText(commentStatus.getContent());
        return convertView;
    }
    public Account getUser(String userName){
        Account account = null;
        for (Account acc: StartActivity.database.getAllAccounts()
        ) {
            if(acc.getUsername().equals(userName)) account = acc;
        }
        return account;
    }
}
