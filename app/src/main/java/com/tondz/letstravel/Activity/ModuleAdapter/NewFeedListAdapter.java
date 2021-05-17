package com.tondz.letstravel.Activity.ModuleAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tondz.letstravel.Activity.Admin.NewsManagementActivity;
import com.tondz.letstravel.Activity.Admin.UpdateNewsActivity;
import com.tondz.letstravel.Activity.User.UserActivity;
import com.tondz.letstravel.Controller.Login;
import com.tondz.letstravel.Model.*;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.util.ArrayList;
import java.util.List;

public class NewFeedListAdapter extends ArrayAdapter<News> {
    List<News> newsList;
    List<News> suggestions;
    Context context;
    LayoutInflater inflater;

    public NewFeedListAdapter(@NonNull Context context, int resource, List<News> newsList) {
        super(context, resource);
        this.newsList = newsList;
        this.context = context;
        suggestions = newsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Nullable
    @Override
    public News getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (News news : StartActivity.database.getAllNews()) {
                    if (news.getContent().toLowerCase().startsWith(constraint.toString().toLowerCase()) || news.getTitle().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(news);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<News> filteredList = (List<News>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (News news : filteredList) {
                    add(news);
                }
                notifyDataSetChanged();
            }
        }
    };

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_listview_newfeeds, null);
        TextView tv_content = convertView.findViewById(R.id.tv_customlistiview_newfeed_content);
        TextView tv_title = convertView.findViewById(R.id.tv_customlistiview_newfeed_title);
        ImageView img_thumb = convertView.findViewById(R.id.img_customlistiview_newfeed_thumbnail);
        News news = newsList.get(position);
        tv_content.setText(news.getContent());
        tv_title.setText(news.getTitle());
        img_thumb.setImageBitmap(BitmapFactory.decodeByteArray(news.getImage(), 0, news.getImage().length));
        //comment
        CommentNewsAdapter commentNewsAdapter = new CommentNewsAdapter(getContext(), getCommentNews(news.getId()));

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (checkUser(Login.account)) {
                    alertDialogLongClickItemUser(position);
                } else {
                    dialogClickItemAdmin(position);
                }
                return true;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.listview_comment_status);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ListView lv_comment = dialog.findViewById(R.id.lv_commnent_status);
                lv_comment.setAdapter(commentNewsAdapter);
                lv_comment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialogLongClickCommentAdmin(position,news.getId());
                        commentNewsAdapter.notifyDataSetChanged();
                        lv_comment.invalidateViews();
                        return false;
                    }
                });
                dialog.show();
            }
        });
        img_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.image_view);
                ImageView img = dialog.findViewById(R.id.img_view);
                img.setImageDrawable(img_thumb.getDrawable());
                dialog.show();
            }
        });

        //add comment news
        EditText edt_comment = convertView.findViewById(R.id.edt_customlistiview_newfeed_comment);
        Button btn_comment = convertView.findViewById(R.id.btn_customlistiview_newfeed_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentNews commentNews = new CommentNews(autoIDCommentNews(), news.getId(), Login.account.getUsername(), edt_comment.getText().toString());
                StartActivity.database.addCommentNews(commentNews);
                edt_comment.setText("");
            }
        });

        return convertView;
    }

    private String autoIDCommentNews() {
        String ID = "CMN";
        int count = 0;
        for (CommentNews commentNews : StartActivity.database.getAllCommentNews()
        ) {
            if ((ID + count).equals(commentNews.getId())) count++;
        }
        return ID + count;
    }

    private void alertDialogLongClickCommentAdmin(int position,String idNews) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Option");
        CommentNews commentNews = StartActivity.database.getAllCommentNews().get(position);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StartActivity.database.deleteCommentNews(StartActivity.database.getAllCommentNews().get(position));
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dia = new Dialog(getContext());
                dia.setContentView(R.layout.custom_dialog_editcomment);
                EditText edt = dia.findViewById(R.id.edt_edit_comment);
                Button btn = dia.findViewById(R.id.btn_edit_comment);
                dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActivity.database.editCommentNews(new CommentNews(commentNews.getId(),idNews,Login.account.getUsername(),edt.getText().toString()));
                        edt.setText("");
                        dia.dismiss();
                        notifyDataSetChanged();
                        Toast.makeText(getContext(),"Successfully",Toast.LENGTH_LONG).show();
                    }
                });
                dia.show();

            }
        });
        builder.show();
    }

    public static Places places = null;

    private void alertDialogLongClickItemUser(int position) {
        getNews = StartActivity.database.getAllNews().get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Do you want to view places?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                places = getPlaces(StartActivity.database.getAllNews().get(position).getId_places());
                UserActivity.tabLayout.selectTab(UserActivity.tabLayout.getTabAt(1));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private boolean checkUser(Account account) {
        if (!account.getUsername().equals("admin")) return true;
        else return false;
    }

    private List<CommentNews> getCommentNews(String idNews) {
        List<CommentNews> commentNewsList = new ArrayList<>();
        for (CommentNews cmt : StartActivity.database.getAllCommentNews()
        ) {
            if (cmt.getId_news().equals(idNews)) {
                commentNewsList.add(cmt);
            }
        }
        return commentNewsList;
    }

    public static News getNews = null;

    private void dialogClickItemAdmin(int position) {
        getNews = StartActivity.database.getAllNews().get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Options");
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(getContext(), UpdateNewsActivity.class));
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StartActivity.database.deleteNews(getNews);
                deleteCommentNews(getNews.getId());
                dialog.dismiss();
                context.startActivity(new Intent(getContext(), NewsManagementActivity.class));
                notifyDataSetChanged();
            }
        });
        builder.show();
    }

    private void deleteCommentNews(String idNews) {
        for (CommentNews commentNews : StartActivity.database.getAllCommentNews()
        ) {
            if (commentNews.getId_news().equals(idNews)) {
                StartActivity.database.deleteCommentNews(commentNews);
            }
        }
    }

    private Places getPlaces(String id_Places) {
        Places result = new Places();
        for (Places places : StartActivity.database.getAllPlaces()
        ) {
            if (places.getId().equals(id_Places)) result = places;
        }
        return result;
    }
}
