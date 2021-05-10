package com.tondz.letstravel.Activity.User.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    public List<HashMap<String,String>> parse(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            {
                jsonObject = new JSONObject(jsonData);
                jsonArray = jsonObject.getJSONArray("results");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
        return getPlaces(jsonArray);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<HashMap<String,String>>placesList = new ArrayList<>();
        HashMap<String,String> placeMap = null;
        for (int i = 0; i < placesCount; i++) {
            try {
                placeMap = getPlace((JSONObject)jsonArray.get(i));
                placesList.add(placeMap);
            }catch (JSONException e){

            }
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String,String>googlePlaceMap = new HashMap<String,String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longtitude = "";
        String reference = "";
        try{
            if(!googlePlaceJson.isNull("name")){
                placeName = googlePlaceJson.getString("name");
            }
            if(!googlePlaceJson.isNull("vicinity")){
                placeName = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longtitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name",placeName);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longtitude);
            googlePlaceMap.put("reference",reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }

}
