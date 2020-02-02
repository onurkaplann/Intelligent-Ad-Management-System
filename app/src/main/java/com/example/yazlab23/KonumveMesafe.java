package com.example.yazlab23;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class KonumveMesafe extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    EditText longitude,latitude,aralik,magazaadi;
    public static String longitudetut,latitudetut,araliktut,magazaaditut;

    Button konum,ara;

    public static Location l1;

    Spinner girisSpinner;
    ArrayAdapter<CharSequence> myAdapter;
    public static String Spinnertut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konumve_mesafe);

        ActivityCompat.requestPermissions(KonumveMesafe.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        girisSpinner = (Spinner) findViewById(R.id.spinnerKonumveMesafe);
        myAdapter =  ArrayAdapter.createFromResource(this,R.array.spinnerKonumveMesafe,android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        girisSpinner.setAdapter(myAdapter);
        girisSpinner.setOnItemSelectedListener(this);

        longitude = findViewById(R.id.editTextKonumveMesafeLongitude);
        latitude = findViewById(R.id.editTextKonumveMesafeLatitude);
        aralik = findViewById(R.id.editTextKonumveMesafeAralık);
        magazaadi = findViewById(R.id.editTextKonumveMesafeMagazaAdi);

        konum = findViewById(R.id.buttonKonumveMesafeDegerleriAl);
        konum.setOnClickListener(this);
        ara = findViewById(R.id.buttonKonumveMesafeAra);
        ara.setOnClickListener(this);
        l1 = new Location("point1");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinnertut = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == konum.getId()){
            GPStracker g = new GPStracker(getApplicationContext());
            l1 = g.getLocation();
            if(l1 != null){
                double lat= l1.getLatitude();
                double lon = l1.getLongitude();
                longitude.setText(""+lon);
                latitude.setText(""+lat);
                //Toast.makeText(getApplicationContext(),"LAT :"+lat+"\n LON : "+lon,Toast.LENGTH_LONG).show();
            }

        }
        if(v.getId() == ara.getId()){
            longitudetut = longitude.getText().toString();
            latitudetut = latitude.getText().toString();
            araliktut = aralik.getText().toString();
            magazaaditut = magazaadi.getText().toString();
            l1.setLatitude(Double.parseDouble(latitudetut));
            l1.setLongitude(Double.parseDouble(longitudetut));
            Intent MagazaListele = new Intent(KonumveMesafe.this, MagazaListele.class);
            startActivity(MagazaListele);
        }
    }



}
