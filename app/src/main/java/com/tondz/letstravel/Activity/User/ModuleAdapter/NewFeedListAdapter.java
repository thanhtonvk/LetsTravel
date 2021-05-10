package com.tondz.letstravel.Activity.User.ModuleAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tondz.letstravel.Model.News;
import com.tondz.letstravel.R;

import java.util.ArrayList;

public class NewFeedListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<News>newFeedsArrayList;
    public NewFeedListAdapter(Context context,ArrayList<News>newFeeds){
        this.context = context;
        this.newFeedsArrayList = newFeeds;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newFeedsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder{
        public TextView tv_title;
        public TextView tv_content;
        public ImageView img_thumbnail;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.custom_listview_newfeeds,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_content = convertView.findViewById(R.id.custom_listview_tv_content);
            viewHolder.tv_title = convertView.findViewById(R.id.custom_listview_tv_title);
            viewHolder.img_thumbnail = convertView.findViewById(R.id.custom_listview_img_thumbnail);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        News newFeeds = newFeedsArrayList.get(position);
        viewHolder.tv_title.setText(newFeeds.getContent());
        viewHolder.tv_content.setText(newFeeds.getContent());
        //viewHolder.img_thumbnail.setImageResource(newFeeds.getImage());
        return convertView;
    }
}
