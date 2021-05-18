package com.tondz.letstravel.Activity.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.util.Arrays;
import java.util.List;

public class UpdatePlacesActivity extends FragmentActivity implements OnMapReadyCallback {

    EditText edt_name;
    Button edt_places_search;
    Button btn_update;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient client;
    GoogleMap mMap;
    public static LatLng position = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_places);
        initView();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment_placesmanager);
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

    private void initView() {
        edt_name = findViewById(R.id.edt_placesmanager_name);
        edt_places_search = findViewById(R.id.edt_placesmanager_map_search);
        btn_update = findViewById(R.id.btn_placesmanager_update);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
            Status status = Autocomplete.getStatusFromIntent(data);
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
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.tondz.letstravel.Model.Places places = new com.tondz.letstravel.Model.Places(autoID(), edt_name.getText().toString(), position);
                StartActivity.database.addPlaces(places);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), PlacesManagerActivity.class));
                finish();
            }
        });
        if (PlacesManagerActivity.places != null) {
            btn_update.setText("Update");
            edt_name.setText(PlacesManagerActivity.places.getName());
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    mMap.addMarker(new MarkerOptions().position(PlacesManagerActivity.places.getLatLng()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PlacesManagerActivity.places.getLatLng(), 18));
                }
            });
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == null) {
                        position = PlacesManagerActivity.places.getLatLng();
                    }
                    com.tondz.letstravel.Model.Places places = new com.tondz.letstravel.Model.Places(PlacesManagerActivity.places.getId(), edt_name.getText().toString(), position);
                    StartActivity.database.editPlaces(places);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), PlacesManagerActivity.class));
                    btn_update.setText("Add");
                    position = null;
                    finish();
                }
            });
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

    private String autoID() {
        String code = "PLACE";
        int count = 0;
        for (com.tondz.letstravel.Model.Places places : StartActivity.database.getAllPlaces()
        ) {
            if (places.getId().equals(code + count)) count++;
        }
        return code + count;
    }
}