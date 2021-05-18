package com.tondz.letstravel.Activity.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.tondz.letstravel.Model.Places;
import com.tondz.letstravel.R;
import com.tondz.letstravel.StartActivity;

public class PlacesManagerActivity extends Activity {

    ListView lv_placeslist;
    AutoCompleteTextView edt_search;
    Button btn_add;
    ArrayAdapter<Places>placesArrayAdapter;
    public static Places places = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_manager);
        init();
        eventClick();
        placesArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, StartActivity.database.getAllPlaces());
        edt_search.setAdapter(placesArrayAdapter);
        lv_placeslist.setAdapter(placesArrayAdapter);


    }
    private void init() {
        edt_search = findViewById(R.id.edt_placesmanager_search);
        btn_add = findViewById(R.id.btn_placesmanager_add);
        lv_placeslist = findViewById(R.id.lv_admin_placesmanager);
    }
    private void eventClick(){
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                places =null;
                startActivity(new Intent(getApplicationContext(),UpdatePlacesActivity.class));
                finish();

            }
        });
        lv_placeslist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                places = StartActivity.database.getAllPlaces().get(position);
                dialogLongClick();
                return false;
            }
        });
    }
    private void dialogLongClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StartActivity.database.deletePlaces(places);

            }
        });
        builder.setNegativeButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),UpdatePlacesActivity.class));
                finish();
            }
        });
        builder.show();



    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),MenuAdmin.class));
    }
}