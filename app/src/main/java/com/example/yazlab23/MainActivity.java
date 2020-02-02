package com.example.yazlab23;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button Giris,Kayitol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Giris = findViewById(R.id.buttonBaslangicGiris);
        Giris.setOnClickListener(this);
        Kayitol = findViewById(R.id.buttonBaslangicKayitol);
        Kayitol.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == Giris.getId()){
            Intent Giris = new Intent(MainActivity.this,Giris.class);
            startActivity(Giris);
        }
        else if(v.getId() == Kayitol.getId()){
            Intent KayitOl = new Intent(MainActivity.this,KayitOl.class);
            startActivity(KayitOl);
        }
    }
}
