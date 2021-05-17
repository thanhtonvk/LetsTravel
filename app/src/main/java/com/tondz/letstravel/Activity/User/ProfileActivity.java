package com.tondz.letstravel.Activity.User;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.tondz.letstravel.Activity.ModuleAdapter.StatusAdapter;
import com.tondz.letstravel.Controller.Login;
import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.Model.Status;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ImageView img_avatar,img_iconavatar;
    TextView tv_name;
    Button btn_addstt;
    ListView lv_stt;
    AutoCompleteTextView edt_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        StatusAdapter adapter= new StatusAdapter(this,R.layout.custom_listview_status, getstatusList());
        edt_search.setAdapter(adapter);
        lv_stt.setAdapter(adapter);
        img_avatar.setImageBitmap(BitmapFactory.decodeByteArray(Login.account.getAvatar(),0,Login.account.getAvatar().length));
        img_iconavatar.setImageBitmap(BitmapFactory.decodeByteArray(Login.account.getAvatar(),0,Login.account.getAvatar().length));
        btn_addstt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UpdateStatusActivity.class));
            }
        });
        tv_name.setText(Login.account.getFullname());
        img_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog= new Dialog(ProfileActivity.this);
                dialog.setContentView(R.layout.custom_dialog_choose_image);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button btn_viewavatar = dialog.findViewById(R.id.btn_dialog_choose_from_folder);
                btn_viewavatar.setText("View avatar");
                Button btn_edit_info = dialog.findViewById(R.id.btn_dialog_capture);
                btn_edit_info.setText("Edit profile");
                btn_viewavatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog d1 = new Dialog(ProfileActivity.this);
                        d1.setContentView(R.layout.image_view);
                        d1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        ImageView img = d1.findViewById(R.id.img_view);
                        img.setImageDrawable(img_avatar.getDrawable());
                        d1.show();
                    }
                });
                btn_edit_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editProfile();
                    }
                });
                dialog.show();
            }
        });
    }
    ImageView avatar;
    private void editProfile(){
        Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.setContentView(R.layout.register_tab_fragment);
        avatar= dialog.findViewById(R.id.btn_choose_avatar);
        EditText edt_username = dialog.findViewById(R.id.edt_register_username);
        EditText edt_fullname = dialog.findViewById(R.id.edt_register_fullname);
        EditText edt_dateofbirth = dialog.findViewById(R.id.edt_register_dateofbirth);
        EditText edt_password = dialog.findViewById(R.id.edt_register_password);
        EditText edt_prepassword = dialog.findViewById(R.id.edt_register_prepassword);
        EditText edt_phonenumber  = dialog.findViewById(R.id.edt_register_phonenumber);
        EditText edt_email = dialog.findViewById(R.id.edt_register_email);
        Button btn_update = dialog.findViewById(R.id.btn_register_createaccount);
        avatar.setImageBitmap(BitmapFactory.decodeByteArray(Login.account.getAvatar(),0,Login.account.getAvatar().length));
        edt_username.setText(Login.account.getUsername());
        edt_username.setEnabled(false);
        edt_fullname.setText(Login.account.getFullname());
        edt_dateofbirth.setText(Login.account.getDateofbirth());
        edt_password.setText(Login.account.getPassword());
        edt_prepassword.setText(Login.account.getPassword());
        edt_phonenumber.setText(Login.account.getPhonenumber());
        edt_email.setText(Login.account.getEmail());
        btn_update.setText("Update");
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChooseImage();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account acc= new Account(edt_username.getText().toString(),edt_password.getText().toString(),edt_fullname.getText().toString(),edt_dateofbirth.getText().toString(),edt_email.getText().toString(),edt_phonenumber.getText().toString(),convertToArrayByte(avatar));
                StartActivity.database.editAccount(acc);
                Toast.makeText(getApplicationContext(),"Successfully",Toast.LENGTH_LONG).show();
                dialog.dismiss();
                finish();
                startActivity(getIntent());
            }
        });
        dialog.show();

    }
    public byte[] convertToArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return imagemTratada(stream.toByteArray());
    }
    private byte[] imagemTratada(byte[] imagem_img) {
        while (imagem_img.length > 500000) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.8), (int) (bitmap.getHeight() * 0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;
    }
    private void initView(){
        img_avatar = findViewById(R.id.img_profile_avatar);
        tv_name = findViewById(R.id.tv_profile_name);
        btn_addstt = findViewById(R.id.btn_profile_checkin);
        img_iconavatar= findViewById(R.id.img_profile_icavatar);
        lv_stt = findViewById(R.id.lv_profile_status);
        edt_search = findViewById(R.id.autoComplete_profile_search);
    }
    private List<Status> getstatusList(){
        List<Status>statusList= new ArrayList<>();
        for (Status status:StartActivity.database.getAllStatus()
             ) {
            if(status.getUsername().equals(Login.account.getUsername())) statusList.add(status);
        }
        return statusList;
    }
    int REQUEST_CODE_CAPTURE=123;
    int REQUEST_CODE_FOLDER = 456;
    private void dialogChooseImage() {
        Button btn_choose_folder, btn_capture;
        Dialog dialog = new Dialog(ProfileActivity.this);
        dialog.setContentView(R.layout.custom_dialog_choose_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //init dialog
        btn_choose_folder = dialog.findViewById(R.id.btn_dialog_choose_from_folder);
        btn_capture = dialog.findViewById(R.id.btn_dialog_capture);

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAPTURE);
                dialog.dismiss();
            }
        });
        btn_choose_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}