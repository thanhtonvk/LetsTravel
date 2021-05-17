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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.tondz.letstravel.Activity.User.Fragment.StatusTabFragment;
import com.tondz.letstravel.Activity.User.UpdateStatusActivity;
import com.tondz.letstravel.Activity.User.UserActivity;
import com.tondz.letstravel.Controller.Login;
import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.Model.CommentStatus;
import com.tondz.letstravel.Model.Places;
import com.tondz.letstravel.Model.Status;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends ArrayAdapter<Status> {

    List<Status> statusList;
    List<Status> suggestions;
    Context context;
    LayoutInflater inflater;
    public  static LatLng places= null;
    public static Status getStatus = null;
    public StatusAdapter(@NonNull Context context, int resource, List<Status> statusList) {
        super(context, resource);
        this.statusList = statusList;
        this.context = context;
        suggestions = statusList;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return statusList.size();
    }

    @Nullable
    @Override
    public Status getItem(int position) {
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
                for (Status status : StartActivity.database.getAllStatus()) {
                    if (status.getContent().toLowerCase().startsWith(constraint.toString().toLowerCase()) || status.getContent().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(status);
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
            List<Status> filteredList = (List<Status>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Status news : filteredList) {
                    add(news);
                }
                notifyDataSetChanged();
            }
        }
    };

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(R.layout.custom_listview_status, null);
        TextView tv_name = convertView.findViewById(R.id.tv_customlistiview_status_name);
        TextView tv_content = convertView.findViewById(R.id.tv_customlistiview_status_content);
        ImageView img = convertView.findViewById(R.id.img_customlistiview_status_thumbnail);
        ImageView img_avatar = convertView.findViewById(R.id.img_customlistview_status_avatar);
        Status status = statusList.get(position);
        Account account = getAccount(status.getUsername());
        tv_name.setText(account.getFullname());
        img_avatar.setImageBitmap(BitmapFactory.decodeByteArray(account.getAvatar(), 0, account.getAvatar().length));
        tv_content.setText(status.getContent());
        img.setImageBitmap(BitmapFactory.decodeByteArray(status.getImage(), 0, status.getImage().length));
        CommentStatusAdapter commentStatusAdapter = new CommentStatusAdapter(getContext(), getCommentStatus(status.getId()));
        //show dialog image
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.image_view);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView imghihi = dialog.findViewById(R.id.img_view);
                imghihi.setImageDrawable(img.getDrawable());
                dialog.show();
            }
        });
        //comment
        EditText edt_contentcomment = convertView.findViewById(R.id.edt_customlistiview_status_comment);
        Button btn_comment = convertView.findViewById(R.id.btn_customlistiview_status_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentStatus commentStatus = new CommentStatus(autoIDComment(),status.getId(),Login.account.getUsername(),edt_contentcomment.getText().toString());
                StartActivity.database.addCommentStatus(commentStatus);
                edt_contentcomment.setText("");
            }
        });
        //view comment
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.listview_comment_status);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ListView lv_comment = dialog.findViewById(R.id.lv_commnent_status);
                lv_comment.setAdapter(commentStatusAdapter);
                lv_comment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        if (checkAdmin(Login.account)) {
                            dialogCheckAdminCmt(position, status.getId());
                        } else if (checkPermissionComment(getCommentStatus(status.getId()).get(position), Login.account)) {
                            dialogCheckPerComment(position, status.getId());
                        } else {
                            //
                        }
                        lv_comment.invalidateViews();
                        return false;
                    }
                });
                dialog.show();
            }
        });
        //set long click listview
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(checkPermissionStatus(status,Login.account)){
                    dialogCheckPerStatus(position);
                }
                else if(checkAdmin(Login.account)){
                    dialogCheckAdminStatus(position);
                }else{
                    //view places
                    dialogLongClickUser(position);
                }
                return true;
            }
        });

        return convertView;
    }

    private void dialogLongClickUser(int position){
        getStatus = StartActivity.database.getAllStatus().get(position);
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        builder.setTitle("Do you want to view places?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                places = getStatus.getLatLng();
                UserActivity.tabLayout.selectTab(UserActivity.tabLayout.getTabAt(1));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();;
            }
        });
        builder.show();
    }
    private String autoIDComment(){
        String ID = "CMTSTT";
        int count = 0;
        for (CommentStatus commentStatus:StartActivity.database.getAllCommenStatuss()
             ) {
            if((ID+count).equals(commentStatus.getId())) count++;
        }
        return ID+count;
    }
    private boolean checkPermissionStatus(Status status, Account account) {
        if (status.getUsername().equals(account.getUsername())) return true;
        else return false;
    }

    private boolean checkPermissionComment(CommentStatus commentStatus, Account account) {
        if (commentStatus.getUsername().equals(account.getUsername())) return true;
        else return false;
    }

    private boolean checkAdmin(Account account) {
        if (!account.getPermission().equals("user")) return true;
        else return false;
    }

    private void dialogCheckPerStatus(int positon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Options");
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //edit stt
                getStatus = StartActivity.database.getAllStatus().get(positon);
                getContext().startActivity(new Intent(getContext(), UpdateStatusActivity.class));
            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StartActivity.database.deleteStatus(statusList.get(positon));
                Toast.makeText(getContext(), "Delete Successfully", Toast.LENGTH_LONG).show();
                deleteAllComment(StartActivity.database.getAllStatus().get(positon).getId());
                setNotifyOnChange(true);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void dialogCheckPerComment(int position, String idStatus) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        CommentStatus commentStatus = getCommentStatus(idStatus).get(position);
        builder.setTitle("Options");
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //edit cmt
                Dialog dia = new Dialog(getContext());
                dia.setContentView(R.layout.custom_dialog_editcomment);
                EditText edt = dia.findViewById(R.id.edt_edit_comment);
                Button btn = dia.findViewById(R.id.btn_edit_comment);
                dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StartActivity.database.editCommentStatus(new CommentStatus(commentStatus.getId(),idStatus,Login.account.getUsername(),edt.getText().toString()));
                        edt.setText("");
                        dia.dismiss();
                        notifyDataSetChanged();
                        Toast.makeText(getContext(),"Successfully",Toast.LENGTH_LONG).show();
                    }
                });
                dia.show();
            }
        });
        builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StartActivity.database.deleteCommentStatus(commentStatus);
                Toast.makeText(getContext(), "Delete Successfully", Toast.LENGTH_LONG);
               notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void dialogCheckAdminStatus(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Options");
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StartActivity.database.deleteStatus(statusList.get(position));
                Toast.makeText(getContext(), "Delete Successfully", Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void dialogCheckAdminCmt(int position, String idStatus) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        CommentStatus commentStatus = getCommentStatus(idStatus).get(position);
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StartActivity.database.deleteCommentStatus(commentStatus);
                Toast.makeText(getContext(), "Delete Successfully", Toast.LENGTH_LONG);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private Account getAccount(String username) {
        Account ac = new Account();
        for (Account account : StartActivity.database.getAllAccounts()
        ) {
            if (username.equals(account.getUsername())) ac = account;
        }
        return ac;
    }

    private List<CommentStatus> getCommentStatus(String idStatus) {
        List<CommentStatus> commentStatusList = new ArrayList<>();
        for (CommentStatus commentStatus : StartActivity.database.getAllCommenStatuss()
        ) {
            if (commentStatus.getId_status().equals(idStatus)) commentStatusList.add(commentStatus);
        }
        return commentStatusList;
    }
    private void deleteAllComment(String idStatus){
        for (CommentStatus commentStatus:StartActivity.database.getAllCommenStatuss()
             ) {
            if(commentStatus.getId_status().equals(idStatus)) StartActivity.database.deleteCommentStatus(commentStatus);
        }
    }
}
