package com.tondz.letstravel.Activity.User.Adapter;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {
        try {
            mMap = (GoogleMap) objects[0];
            url = (String) objects[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (Exception e){

        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearbyPlaceList = null;
        DataParser dataParser = new DataParser();
        nearbyPlaceList = dataParser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }
    private void showNearbyPlaces(List<HashMap<String,String>>nearbyPlacesList){
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String,String>googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vincinity = googlePlace.get("vicinity");
            LatLng latLng= new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName+" : "+vincinity);
            mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        }
    }
}
