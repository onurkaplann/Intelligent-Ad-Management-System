package com.example.yazlab23;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class MagazaListele extends AppCompatActivity implements LocationListener {

    String firmaid,firmaadi,firmalocation,kampanyaicerik,kampanyasuresi;
    String longitudetut = KonumveMesafe.longitudetut;
    String latitudetut = KonumveMesafe.latitudetut;
    String araliktut = KonumveMesafe.araliktut;
    String magazaaditut = KonumveMesafe.magazaaditut;
    String Spinnertut = KonumveMesafe.Spinnertut;

    Location l1 = KonumveMesafe.l1;

    Location l2,l3,l4;
    int tampon = 0;

    double mesafe;

    double longitudetut2=0,latitudetut2=0,araliktut2=0;

    ArrayList<Magaza> arananlar = new ArrayList<Magaza>();
    ArrayList<Magaza> kalanlar = new ArrayList<Magaza>();

    LinearLayout linearlayoutMagazaListele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magaza_listele);

        //ActivityCompat.requestPermissions(MagazaListele.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        linearlayoutMagazaListele = findViewById(R.id.linearlayoutMagazaListele);
        l2 = new Location("point2");
        l3 = new Location("point3");
        l4 = new Location("point4");
        pulldata();
        ActivityCompat.requestPermissions(MagazaListele.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        Context context = getApplicationContext() ;
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context,"Permission not granted",Toast.LENGTH_SHORT).show();

        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);

    }

    public void pulldata(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference magazalar = db.getReference().child("Magaza");
        magazalar.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                firmalocation = dataSnapshot.child("firmalokasyon").getValue().toString();
                //System.out.println("YAZ:"+firmalocation);
                String[] kelime = null;
                kelime = firmalocation.split(",");
                latitudetut2 = Double.parseDouble(kelime[1]);
                longitudetut2 = Double.parseDouble(kelime[0]);

              //  System.out.println("lat:"+latitudetut2);
                //System.out.println("log:"+longitudetut2);

                l2.setLatitude(latitudetut2);
                l2.setLongitude(longitudetut2);


                mesafe = CalculateDistance(l1,l2);

                 //System.out.println("dis:"+mesafe);

                // System.out.println("log="+longitudetut2+"lat="+latitudetut2);

                araliktut2 = Double.parseDouble(araliktut);

                kampanyaicerik = dataSnapshot.child("kampanyaicerik").getValue().toString();

                firmaadi = dataSnapshot.child("firmaadi").getValue().toString();

                firmaid = dataSnapshot.getKey().toString();

                kampanyasuresi = dataSnapshot.child("kampanyasuresi").getValue().toString();



                if(magazaaditut.equals("") && Spinnertut.equals("")){
                    if(mesafe <= araliktut2){
                        System.out.println("1");
                        arananlar.add(new Magaza(mesafe, firmaid, firmaadi,firmalocation, kampanyaicerik, kampanyasuresi,firmaid+"jpg"));
                        Magaza deneme = new Magaza(mesafe,firmaid,firmaadi,firmalocation,kampanyaicerik,kampanyasuresi,firmaid+".jpg");
                        ObjeKart nesne = new ObjeKart(getApplicationContext(),deneme);
                        linearlayoutMagazaListele.addView(nesne);
                        linearlayoutMagazaListele.addView(new ObjeBilgi(getApplicationContext(),1));
                        tampon = 0;
                    }else {
                        kalanlar.add(new Magaza(mesafe,firmaid,firmaadi,firmalocation,kampanyaicerik,kampanyasuresi,firmaid+".jpg"));
                    }
                }




                if(!magazaaditut.equals("") && Spinnertut.equals("")){
                    if(mesafe <= araliktut2 && firmaadi.equals(magazaaditut)){
                        System.out.println("2");
                        arananlar.add(new Magaza(mesafe, firmaid, firmaadi, firmalocation,kampanyaicerik, kampanyasuresi,firmaid+"jpg"));
                        Magaza deneme = new Magaza(mesafe,firmaid,firmaadi,firmalocation,kampanyaicerik,kampanyasuresi,firmaid+".jpg");
                        ObjeKart nesne = new ObjeKart(getApplicationContext(),deneme);
                        linearlayoutMagazaListele.addView(nesne);
                        linearlayoutMagazaListele.addView(new ObjeBilgi(getApplicationContext(),1));
                        tampon = 2;
                    }
                    else{
                        kalanlar.add(new Magaza(mesafe,firmaid,firmaadi,firmalocation,kampanyaicerik,kampanyasuresi,firmaid+".jpg"));
                    }
                }

                if(magazaaditut.equals("") && !Spinnertut.equals("")){
                    if(mesafe <= araliktut2 && kampanyaicerik.equals(Spinnertut)){
                        System.out.println("3");
                        arananlar.add(new Magaza(mesafe, firmaid, firmaadi,firmalocation, kampanyaicerik, kampanyasuresi,firmaid+"jpg"));
                        Magaza deneme = new Magaza(mesafe,firmaid,firmaadi,firmalocation,kampanyaicerik,kampanyasuresi,firmaid+".jpg");
                        ObjeKart nesne = new ObjeKart(getApplicationContext(),deneme);
                        linearlayoutMagazaListele.addView(nesne);
                        linearlayoutMagazaListele.addView(new ObjeBilgi(getApplicationContext(),1));
                    }
                    else{
                        kalanlar.add(new Magaza(mesafe,firmaid,firmaadi,firmalocation,kampanyaicerik,kampanyasuresi,firmaid+".jpg"));
                    }
                }

                if(!magazaaditut.equals("") && !Spinnertut.equals("")){
                    if(mesafe <= araliktut2 && kampanyaicerik.equals(Spinnertut) && firmaadi.equals(magazaaditut)){
                        System.out.println("4");
                        arananlar.add(new Magaza(mesafe, firmaid, firmaadi,firmalocation, kampanyaicerik, kampanyasuresi,firmaid+"jpg"));
                        Magaza deneme = new Magaza(mesafe,firmaid,firmaadi,firmalocation,kampanyaicerik,kampanyasuresi,firmaid+".jpg");
                        ObjeKart nesne = new ObjeKart(getApplicationContext(),deneme);
                        linearlayoutMagazaListele.addView(nesne);
                        linearlayoutMagazaListele.addView(new ObjeBilgi(getApplicationContext(),1));
                    }
                    else{
                        kalanlar.add(new Magaza(mesafe,firmaid,firmaadi,firmalocation,kampanyaicerik,kampanyasuresi,firmaid+".jpg"));
                    }
                }

                /*if(magazaaditut.equals("")) {
                    System.out.println("BOŞ BOŞ BOŞ");
                    System.out.println(Spinnertut);
                    if (mesafe <= araliktut2 && kampanyaicerik.equals(Spinnertut)) {
                        System.out.println("AAAA");
                        firmaid = dataSnapshot.getKey().toString();
                        kampanyasuresi = dataSnapshot.child("kampanyasuresi").getValue().toString();
                        //list.add(new Magaza(mesafe, firmaid, firmaadi, kampanyaicerik, kampanyasuresi,firmaid+"jpg"));
                    }
                }else if(mesafe <= araliktut2 && kampanyaicerik.equals(Spinnertut) && firmaadi.equals(magazaaditut)){
                    System.out.println("AAAA");
                    firmaid = dataSnapshot.getKey().toString();
                    kampanyasuresi = dataSnapshot.child("kampanyasuresi").getValue().toString();
                    //list.add(new Magaza(mesafe, firmaid, firmaadi, kampanyaicerik, kampanyasuresi,firmaid+".jpg"));
                }*/
                /*for(int i = 0; i<list.size();i++){
                    Toast.makeText(getApplicationContext(),"OF OF"+list.get(i).getFirmaid(),Toast.LENGTH_LONG).show();
                    System.out.println("OFOF:"+list.get(i).getFirmaid());
                }*/

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onLocationChanged(Location location) {
        System.out.println("aaaaa");
        double lat = location.getLatitude();
        double log = location.getLongitude();
        System.out.println("lat:"+location.getLatitude());
        System.out.println("log:"+location.getLongitude());
       l4.setLatitude(lat);
       l4.setLongitude(log);
       for (int i = 0;i < kalanlar.size();i++) {
           kalanlar.get(i).getFirmalokasyon();
           System.out.println("firma:" + kalanlar.get(i).getFirmaid());
           String[] kelime = null;
           kelime = kalanlar.get(i).getFirmalokasyon().split(",");
           double lat2 = Double.parseDouble(kelime[1]);
           double log2 = Double.parseDouble(kelime[0]);

           System.out.println("lat2:" + lat2);
           System.out.println("log2:" + log2);

           l3.setLongitude(log2);
           l3.setLatitude(lat2);

           double dis = CalculateDistance(l4, l3);
           System.out.println("distance:" + dis);

           if (magazaaditut.equals("") && Spinnertut.equals("")) {
               if (dis <= araliktut2) {
                   String firmaid2 = kalanlar.get(i).getFirmaid();
                   String firmaadi2 = kalanlar.get(i).getFirmaadi();
                   String firmalocation2 = kalanlar.get(i).getFirmalokasyon();
                   String kampanyaicerik2 = kalanlar.get(i).getKampanyaicerik();
                   String kampanyasuresi2 = kalanlar.get(i).getKampanyasuresi();
                   Magaza deneme = new Magaza(dis, firmaid2, firmaadi2, firmalocation2, kampanyaicerik2, kampanyasuresi2, firmaid2 + ".jpg");
                   ObjeKart nesne = new ObjeKart(getApplicationContext(), deneme);
                   linearlayoutMagazaListele.addView(nesne);
                   linearlayoutMagazaListele.addView(new ObjeBilgi(getApplicationContext(), 1));
                   showNotification(kalanlar.get(i).getFirmaadi(), kalanlar.get(i).getKampanyasuresi());
                   kalanlar.remove(i);
                   arananlar.add(new Magaza(dis, firmaid2, firmaadi2, firmalocation2, kampanyaicerik2, kampanyasuresi2, firmaadi2 + ".jpg"));
               }
           }

           if (!magazaaditut.equals("") && Spinnertut.equals("")) {
               if (dis <= araliktut2 && kalanlar.get(i).getFirmaadi().equals(magazaaditut)) {
                   String firmaid2 = kalanlar.get(i).getFirmaid();
                   String firmaadi2 = kalanlar.get(i).getFirmaadi();
                   String firmalocation2 = kalanlar.get(i).getFirmalokasyon();
                   String kampanyaicerik2 = kalanlar.get(i).getKampanyaicerik();
                   String kampanyasuresi2 = kalanlar.get(i).getKampanyasuresi();
                   Magaza deneme = new Magaza(dis, firmaid2, firmaadi2, firmalocation2, kampanyaicerik2, kampanyasuresi2, firmaid2 + ".jpg");
                   ObjeKart nesne = new ObjeKart(getApplicationContext(), deneme);
                   linearlayoutMagazaListele.addView(nesne);
                   linearlayoutMagazaListele.addView(new ObjeBilgi(getApplicationContext(), 1));
                   showNotification(kalanlar.get(i).getFirmaadi(), kalanlar.get(i).getKampanyasuresi());
                   kalanlar.remove(i);
                   arananlar.add(new Magaza(dis, firmaid2, firmaadi2, firmalocation2, kampanyaicerik2, kampanyasuresi2, firmaadi2 + ".jpg"));
               }
           }
           if (magazaaditut.equals("") && !Spinnertut.equals("")) {
               if (dis <= araliktut2 && kalanlar.get(i).getKampanyaicerik().equals(Spinnertut)) {
                   String firmaid2 = kalanlar.get(i).getFirmaid();
                   String firmaadi2 = kalanlar.get(i).getFirmaadi();
                   String firmalocation2 = kalanlar.get(i).getFirmalokasyon();
                   String kampanyaicerik2 = kalanlar.get(i).getKampanyaicerik();
                   String kampanyasuresi2 = kalanlar.get(i).getKampanyasuresi();
                   Magaza deneme = new Magaza(dis, firmaid2, firmaadi2, firmalocation2, kampanyaicerik2, kampanyasuresi2, firmaid2 + ".jpg");
                   ObjeKart nesne = new ObjeKart(getApplicationContext(), deneme);
                   linearlayoutMagazaListele.addView(nesne);
                   linearlayoutMagazaListele.addView(new ObjeBilgi(getApplicationContext(), 1));
                   showNotification(kalanlar.get(i).getFirmaadi(), kalanlar.get(i).getKampanyasuresi());
                   kalanlar.remove(i);
                   arananlar.add(new Magaza(dis, firmaid2, firmaadi2, firmalocation2, kampanyaicerik2, kampanyasuresi2, firmaadi2 + ".jpg"));
               }
           }

           if (!magazaaditut.equals("") && !Spinnertut.equals("")) {
               if (dis <= araliktut2 && kalanlar.get(i).getKampanyaicerik().equals(Spinnertut) && kalanlar.get(i).getFirmaadi().equals(magazaaditut)) {
                   String firmaid2 = kalanlar.get(i).getFirmaid();
                   String firmaadi2 = kalanlar.get(i).getFirmaadi();
                   String firmalocation2 = kalanlar.get(i).getFirmalokasyon();
                   String kampanyaicerik2 = kalanlar.get(i).getKampanyaicerik();
                   String kampanyasuresi2 = kalanlar.get(i).getKampanyasuresi();
                   Magaza deneme = new Magaza(dis, firmaid2, firmaadi2, firmalocation2, kampanyaicerik2, kampanyasuresi2, firmaid2 + ".jpg");
                   ObjeKart nesne = new ObjeKart(getApplicationContext(), deneme);
                   linearlayoutMagazaListele.addView(nesne);
                   linearlayoutMagazaListele.addView(new ObjeBilgi(getApplicationContext(), 1));
                   showNotification(kalanlar.get(i).getFirmaadi(), kalanlar.get(i).getKampanyasuresi());
                   kalanlar.remove(i);
                   arananlar.add(new Magaza(dis, firmaid2, firmaadi2, firmalocation2, kampanyaicerik2, kampanyasuresi2, firmaadi2 + ".jpg"));
               }
           }


       }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public Double CalculateDistance(Location L1, Location L2){
        //System.out.println("l1"+L1.getLatitude());
        System.out.println("l2"+L2.getLatitude());
        return Double.valueOf(L1.distanceTo(L2));
    }

    public void showNotification(String title, String body){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID="com.example.myfcm.newChannel";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"NotificationName",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Test channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});

            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");


        notificationManager.notify(new Random().nextInt(),notificationBuilder.build() );
    }
}
