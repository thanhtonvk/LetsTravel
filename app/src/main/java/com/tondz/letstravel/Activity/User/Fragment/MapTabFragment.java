package com.tondz.letstravel.Activity.User.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.tondz.letstravel.R;

import java.util.List;

public class MapTabFragment extends Fragment implements OnMapReadyCallback{
    ViewGroup viewGroup;
    private  GoogleMap map;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient mPlacesClient;
    private List<AutocompletePrediction> predictionList;

    private Location mLastKnowLocation;
    private LocationCallback locationCallback;

    private MaterialSearchBar materialSearchBar;
    private View mapView;
    private Button btn_findRestaurant;

    private final float DEFAULT_ZOOM = 10;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_maps,container,false);
        initView();
//        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
//        mapFragment.getMapAsync(this::onMapReady);

        return viewGroup;
    }
    private void initView(){
        materialSearchBar = viewGroup.findViewById(R.id.btn_mapactivity_findrestaurant);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}
