package com.tondz.letstravel.Activity.User.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.tondz.letstravel.Activity.Admin.PlacesManagerActivity;
import com.tondz.letstravel.Activity.ModuleAdapter.NewFeedListAdapter;
import com.tondz.letstravel.Activity.ModuleAdapter.StatusAdapter;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapTabFragment extends Fragment implements OnMapReadyCallback {
    ViewGroup viewGroup;
    EditText edt_search;
    GoogleMap mMap;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;
    Spinner spinner_typePlaces;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.map_tab_fragment, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        initView();

        Places.initialize(getContext(), getString(R.string.google_maps_key));
        edt_search.setFocusable(false);

        edt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                        , Place.Field.LAT_LNG
                        , Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                        , fieldList).build(getContext());
                startActivityForResult(intent, 100);
            }
        });

        client = LocationServices.getFusedLocationProviderClient(getActivity());
        if (NewFeedListAdapter.places != null) {
            mMap.addMarker(new MarkerOptions().position(NewFeedListAdapter.places.getLatLng()).title(NewFeedListAdapter.places.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NewFeedListAdapter.places.getLatLng(), 18));
            NewFeedListAdapter.places=null;
        }
        if(StatusAdapter.places!=null){
            mMap.addMarker(new MarkerOptions().position(StatusAdapter.places));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(StatusAdapter.places,18));
            StatusAdapter.places =null;
        }
        return viewGroup;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            edt_search.setText(place.getAddress());
            LatLng latLng = place.getLatLng();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                Marker markerName;
                int num = 0;

                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    if (num == 0) {
                        markerName = mMap.addMarker(new MarkerOptions().position(latLng));
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
            Toast.makeText(getContext(), status.getStatusMessage(), Toast.LENGTH_LONG).show();

        }
    }

    @SuppressLint("VisibleForTests")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //find nearby places


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            int num = 0;
            Marker markerName;

            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                if (num == 0) {
                    markerName = mMap.addMarker(new MarkerOptions().position(latLng));
                    num = 1;
                } else {
                    markerName.remove();
                    num = 0;
                }

            }
        });
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        setSpinner_typePlaces();

    }

    private void initView() {
        edt_search = viewGroup.findViewById(R.id.edt_maps_tab_search);
        spinner_typePlaces = viewGroup.findViewById(R.id.spinner_map_typeplaces);
    }

    public void setSpinner_typePlaces() {
        spinner_typePlaces.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, placeNameList));
        spinner_typePlaces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMap.clear();
                List<com.tondz.letstravel.Model.Places> placesList = getPlaces(placeNameList[position]);
                for (com.tondz.letstravel.Model.Places places:placesList
                     ) {
                    mMap.addMarker(new MarkerOptions().position(places.getLatLng()).title(places.getName()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    String[] placeNameList = {"Quanh đây", "ATM", "Ngân hàng", "Bệnh viện", "Rạp phim", "Quán ăn"};
    private List<com.tondz.letstravel.Model.Places> getPlaces(String placeName){
        List<com.tondz.letstravel.Model.Places>placesList = new ArrayList<>();
        for (com.tondz.letstravel.Model.Places places: StartActivity.database.getAllPlaces()
             ) {
            if(places.getName().contains(placeName)) placesList.add(places);
        }
        return placesList;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
