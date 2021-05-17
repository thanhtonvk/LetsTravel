package com.tondz.letstravel.Activity.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.tondz.letstravel.Activity.Admin.PlacesManagerActivity;
import com.tondz.letstravel.Activity.ModuleAdapter.StatusAdapter;
import com.tondz.letstravel.Controller.Login;
import com.tondz.letstravel.Model.News;
import com.tondz.letstravel.Model.Status;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class UpdateStatusActivity extends FragmentActivity implements OnMapReadyCallback {

    EditText edt_content;
    Button btn_post,btn_chooseimage,edt_places_search;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    ImageView img_image;
    GoogleMap mMap;
    public static LatLng position = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        initView();
        eventOnclick();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment_status);
        mapFragment.getMapAsync(this);
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        edt_places_search.setFocusable(true);

        edt_places_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        , Place.Field.LAT_LNG
                        , Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                        , fieldList).build(getApplicationContext());
                startActivityForResult(intent, 100);
            }
        });
        client = LocationServices.getFusedLocationProviderClient(getApplicationContext());
    }
    private void editStatus(){
        if(StatusAdapter.getStatus!=null){
            Status status = StatusAdapter.getStatus;
            edt_content.setText(status.getContent());
            btn_post.setText("Update");
            img_image.setImageBitmap(BitmapFactory.decodeByteArray(status.getImage(),0,status.getImage().length));
            position = status.getLatLng();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,18));
            mMap.addMarker(new MarkerOptions().position(position));
            btn_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Status stt = new Status(status.getId(),edt_content.getText().toString(),convertToArrayByte(img_image),position,Login.account.getUsername());
                    StartActivity.database.editStatus(stt);
                    startActivity(new Intent(getApplicationContext(),UserActivity.class));
                    Toast.makeText(getApplicationContext(),"Succesfully",Toast.LENGTH_LONG).show();
                    btn_post.setText("Post");
                    StatusAdapter.getStatus= null;
                    finish();
                }
            });
        }
    }
    private void initView(){
        edt_content = findViewById(R.id.edt_status_content);
        btn_post = findViewById(R.id.btn_status_post);
        btn_chooseimage = findViewById(R.id.btn_status_chooseimage);
        edt_places_search = findViewById(R.id.edt_status_map_search);
        img_image = findViewById(R.id.img_status_image);
    }
    private void eventOnclick(){
        btn_chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialogChooseImage();
            }
        });
    }
    int REQUEST_CODE_CAPTURE = 123;
    int REQUEST_CODE_FOLDER = 456;
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //process image
        if (requestCode == REQUEST_CODE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_image.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        //process position
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            edt_places_search.setText(place.getAddress());
            LatLng latLng = place.getLatLng();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                Marker markerName;
                int num = 0;

                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    if (num == 0) {
                        markerName = mMap.addMarker(new MarkerOptions().position(latLng));
                        position = new LatLng(markerName.getPosition().latitude, markerName.getPosition().longitude);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        num = 1;
                    } else {
                        markerName.remove();
                        num = 0;

                    }
                }
            });
        } else if (requestCode == AutocompleteActivity.RESULT_ERROR) {
            com.google.android.gms.common.api.Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            int num = 0;
            Marker markerName;

            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if (num == 0) {
                    markerName = mMap.addMarker(new MarkerOptions().position(latLng));
                    position = new LatLng(markerName.getPosition().latitude, markerName.getPosition().longitude);
                    num = 1;
                } else {
                    markerName.remove();
                    num = 0;
                }

            }
        });
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Status status = new Status(autoID(),edt_content.getText().toString(),convertToArrayByte(img_image),position, Login.account.getUsername());
                StartActivity.database.addStatus(status);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                finish();
            }
        });
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        editStatus();
    }
    private String autoID() {
        String code = "STATUS";
        int count = 0;
        for (Status status : StartActivity.database.getAllStatus()
        ) {
            if (status.getId().equals(code + count)) count++;
        }
        return code + count;
    }
}