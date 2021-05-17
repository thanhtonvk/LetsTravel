package com.tondz.letstravel.Activity.ModuleAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tondz.letstravel.Activity.Admin.UserMangerActivity;
import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.Model.CommentNews;
import com.tondz.letstravel.Model.CommentStatus;
import com.tondz.letstravel.Model.News;
import com.tondz.letstravel.Model.Status;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<Account> {


    List<Account> accountList;
    List<Account>suggestions;
    Context context;
    LayoutInflater inflater;
    public UsersAdapter(@NonNull Context context, int resource,List<Account>accountList) {
        super(context, resource);
        this.accountList= accountList;
        this.context = context;
        suggestions = accountList;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return accountList.size();
    }

    @Nullable
    @Override
    public Account getItem(int position) {
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
            if(constraint!=null){
                suggestions.clear();
                for(Account account : StartActivity.database.getAllAccounts()){
                    if(account.getUsername().toLowerCase().startsWith(constraint.toString().toLowerCase())||account.getFullname().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(account);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            }else{
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Account>filteredList = (List<Account>) results.values;
            if(results!=null && results.count>0){
                clear();
                for(Account account: filteredList){
                    add(account);
                }
                notifyDataSetChanged();
            }
        }
    };

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_listview_usersinfo,null);
        ImageView img_avt  = convertView.findViewById(R.id.img_customlistview_user_avatar);
        TextView tv_user = convertView.findViewById(R.id.tv_customlistview_user_username);
        TextView tv_fullname = convertView.findViewById(R.id.tv_customlistview_user_fullname);
        TextView tv_dateofbirth = convertView.findViewById(R.id.tv_customlistview_user_dateofbirth);
        TextView tv_email = convertView.findViewById(R.id.tv_customlistview_user_email);
        TextView tv_phone = convertView.findViewById(R.id.tv_customlistview_user_phonenumber);
        Account account= accountList.get(position);
        img_avt.setImageBitmap(BitmapFactory.decodeByteArray(account.getAvatar(),0,account.getAvatar().length));
        tv_user.setText(account.getUsername());
        tv_fullname.setText(account.getFullname());
        tv_dateofbirth.setText(account.getDateofbirth());
        tv_email.setText(account.getEmail());
        tv_phone.setText(account.getPhonenumber());
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              dialogLongClick(position);
              //
                return false;
            }
        });
        return convertView;
    }
    private void dialogLongClick(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Options");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Account account = StartActivity.database.getAllAccounts().get(position);
                deleteAll(account);
                StartActivity.database.deleteAccount(account);
                notifyDataSetInvalidated();
            }
        });
        builder.show();
    }
    private void deleteAll(Account account){
        for (Status status:StartActivity.database.getAllStatus()
             ) {
            if(status.getUsername().equals(account.getUsername())) StartActivity.database.deleteStatus(status);
        }
        for (CommentNews commentNews:StartActivity.database.getAllCommentNews()
             ) {
            if(commentNews.getUsername().equals(account.getUsername())) StartActivity.database.deleteCommentNews(commentNews);
        }
        for(CommentStatus commentStatus: StartActivity.database.getAllCommenStatuss()){
            if(commentStatus.getUsername().equals(account.getUsername())) StartActivity.database.deleteCommentStatus(commentStatus);
        }

    }

}
