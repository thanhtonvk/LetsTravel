package com.tondz.letstravel.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tondz.letstravel.Controller.Register;
import com.tondz.letstravel.Model.Account;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RegisterTabFragment extends Fragment {
    List<Account> accounts;
    Button btn_register;
    ImageView btn_choose_image;
    ViewGroup viewGroup;
    EditText editText_username, editText_password, editText_prepassword, editText_phonenumber, editText_email, editText_dateofbirth, editText_fullname;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.register_tab_fragment, container, false);
        initView();
        eventOnclick();
        return viewGroup;
    }
    private void initView() {
        accounts = new ArrayList<>();
        btn_register = viewGroup.findViewById(R.id.btn_register_createaccount);
        editText_username = viewGroup.findViewById(R.id.edt_register_username);
        editText_password = viewGroup.findViewById(R.id.edt_register_password);
        editText_prepassword = viewGroup.findViewById(R.id.edt_register_prepassword);
        editText_phonenumber = viewGroup.findViewById(R.id.edt_register_phonenumber);
        editText_dateofbirth = viewGroup.findViewById(R.id.edt_register_dateofbirth);
        editText_email = viewGroup.findViewById(R.id.edt_register_email);
        btn_choose_image = viewGroup.findViewById(R.id.btn_choose_avatar);
        editText_fullname = viewGroup.findViewById(R.id.edt_register_fullname);
    }
    private void eventOnclick() {
        btn_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom_Dialog();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAcount();
            }
        });
    }

    private static final int REQUEST_CODE_CAPTURE = 123;
    private static final int REQUEST_CODE_FOLDER = 234;

    //add Account
    private void addAcount() {
        if (Register.checkUniqueUsername(editText_username.getText().toString())) {
            dialog("Account already exists");
        } else if (!Register.checkPassword(editText_password.getText().toString(), editText_prepassword.getText().toString())) {
            dialog("Pre_password does not like password!!");
        } else {
            Account account = new Account(editText_username.getText().toString(), editText_password.getText().toString(), editText_fullname.getText().toString()
                    , editText_dateofbirth.getText().toString(), editText_email.getText().toString(), editText_phonenumber.getText().toString()
                    , imagemTratada(convertToArrayByte(btn_choose_image)));
            StartActivity.database.addAccount(account);
            dialog("Create account successfully");
        }
    }
    //dialog notify
    private void dialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Warning!");
        builder.setMessage(content);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //choose avatar
    private void custom_Dialog() {
        Button btn_choose_folder, btn_capture;
        Dialog dialog = new Dialog(getContext());
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

    public byte[] convertToArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAPTURE && resultCode == getActivity().RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            btn_choose_image.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == getActivity().RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                btn_choose_image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
