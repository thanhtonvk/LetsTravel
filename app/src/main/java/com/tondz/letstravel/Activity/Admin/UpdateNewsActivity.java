package com.tondz.letstravel.Activity.Admin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tondz.letstravel.Activity.ModuleAdapter.NewFeedListAdapter;
import com.tondz.letstravel.Model.News;
import com.tondz.letstravel.Model.Places;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UpdateNewsActivity extends AppCompatActivity {

    ImageView img;
    EditText edt_titile, edt_content;
    Spinner sp_places;
    ArrayAdapter<String> stringArrayAdapter;
    Button btn_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);
        initView();
        stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getNamePlaces());
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_places.setAdapter(stringArrayAdapter);
        event();
    }

    private void initView() {
        edt_titile = findViewById(R.id.edt_dialog_title_news);
        edt_content = findViewById(R.id.edt_dialog_content_news);
        sp_places = findViewById(R.id.sp_dialog_places_news);
        btn_post = findViewById(R.id.btn_dialog_post_news);
        img = findViewById(R.id.btn_dialog_choose_img_news);
    }

    String idPlaces;

    private void event() {

        sp_places.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idPlaces = getIdPlaces().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idPlaces = getIdPlaces().get(0);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialogChooseImage();
            }
        });
        if (NewFeedListAdapter.getNews != null) {
            btn_post.setText("Update");
            edt_titile.setText(NewFeedListAdapter.getNews.getTitle());
            edt_content.setText(NewFeedListAdapter.getNews.getContent());
            img.setImageBitmap(BitmapFactory.decodeByteArray(NewFeedListAdapter.getNews.getImage(), 0, NewFeedListAdapter.getNews.getImage().length));
            btn_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = new News(NewFeedListAdapter.getNews.getId(), edt_titile.getText().toString(), edt_content.getText().toString(), idPlaces, convertToArrayByte(img));
                    StartActivity.database.editNews(news);
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), NewsManagementActivity.class));
                    finish();
                }
            });
        } else btn_post.setText("Post");
        if (btn_post.getText().equals("Post")) {
            btn_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = new News(autoID(), edt_titile.getText().toString(), edt_content.getText().toString(), idPlaces, convertToArrayByte(img));
                    StartActivity.database.addNews(news);
                    finish();
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), NewsManagementActivity.class));
                    finish();
                }
            });
        }
    }

    int REQUEST_CODE_CAPTURE = 123;
    int REQUEST_CODE_FOLDER = 456;

    //set dialog choose image
    private void setDialogChooseImage() {

        Button btn_choose_folder, btn_capture;
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_choose_image);
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

    private List<String> getIdPlaces() {
        List<String> idPlaces = new ArrayList<>();
        for (Places places : StartActivity.database.getAllPlaces()
        ) {
            idPlaces.add(places.getId());
        }
        return idPlaces;
    }

    private List<String> getNamePlaces() {
        List<String> namePlaces = new ArrayList<>();
        for (Places places : StartActivity.database.getAllPlaces()
        ) {
            namePlaces.add(places.getName());
        }
        return namePlaces;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String autoID() {
        String code = "NEWS";
        int count = 0;
        for (News news : StartActivity.database.getAllNews()
        ) {
            if (news.getId().equals(code + count)) count++;
        }
        return code + count;
    }
}