package com.tondz.letstravel.Activity.User.ModuleAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tondz.letstravel.Model.NewFeeds;
import com.tondz.letstravel.Model.Status;
import com.tondz.letstravel.R;

import java.util.ArrayList;

public class StatusListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Status> statuses;
    public StatusListAdapter(Context context,ArrayList<Status>statuses){
        this.context = context;
        this.statuses = statuses;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return statuses.size();
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
        StatusListAdapter.ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.custom_listview_status,null);
            viewHolder = new StatusListAdapter.ViewHolder();
            viewHolder.tv_content = convertView.findViewById(R.id.custom_listview_status_tv_content);
            viewHolder.tv_title = convertView.findViewById(R.id.custom_listview_status_tv_name);
            viewHolder.img_thumbnail = convertView.findViewById(R.id.custom_listviewstatus_img_thumbnail);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (StatusListAdapter.ViewHolder) convertView.getTag();
        }
        Status status = statuses.get(position);
        viewHolder.tv_title.setText(status.getTitle());
        viewHolder.tv_content.setText(status.getContent());
        viewHolder.img_thumbnail.setImageResource(status.getImage());
        return convertView;
    }
}
