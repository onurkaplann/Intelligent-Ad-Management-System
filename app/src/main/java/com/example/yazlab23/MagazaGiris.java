package com.example.yazlab23;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MagazaGiris extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    EditText firmaadi,firmalokasyon,kampanyasuresi;
    String firmaidtut,firmaaditut,firmalokasyontut,kampanyaiceriktut,kampanyasuresitut;

    ImageView imageViewMagazaGiris;

    Button kaydet,fotograf,konum;
    Location l1;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST=71;
    private StorageReference storageReferance;


    Spinner girisSpinner;
    ArrayAdapter<CharSequence> myAdapter;
    public static String kampanyaicerik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magaza_giris);

        ActivityCompat.requestPermissions(MagazaGiris.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        storageReferance = FirebaseStorage.getInstance().getReference();

        girisSpinner = (Spinner) findViewById(R.id.spinnerMagazaGirisKampanyaIcerik);
        myAdapter =  ArrayAdapter.createFromResource(this,R.array.spinnerMagazaGirisKampanyaIcerik,android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        girisSpinner.setAdapter(myAdapter);
        girisSpinner.setOnItemSelectedListener(this);

        firmaadi = findViewById(R.id.editTextMagazaGirisFirmaAdi);
        firmalokasyon = findViewById(R.id.editTextMagazaGirisFirmaLokasyon);
        kampanyasuresi = findViewById(R.id.editTextMagazaGirisKampanyaSuresi);

        imageViewMagazaGiris = findViewById(R.id.imageViewMagazaGiris);

        kaydet = findViewById(R.id.buttonMagazaGiris);
        kaydet.setOnClickListener(this);
        fotograf = findViewById(R.id.buttonMagazaGirisFotograf);
        fotograf.setOnClickListener(this);
        konum = findViewById(R.id.buttonMagazaGirisKonum);
        konum.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        kampanyaiceriktut = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == kaydet.getId()){
            pushdata2DB();
        }
        if(v.getId() == fotograf.getId()){
            System.out.println("fotograf yukleme butonuna yıklandı");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction((Intent.ACTION_GET_CONTENT));
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
        }
        if(v.getId() == konum.getId()){
            GPStracker g = new GPStracker(getApplicationContext());
            l1 = g.getLocation();
            if(l1 != null){
                double lat= l1.getLatitude();
                double lon = l1.getLongitude();
                firmalokasyon.setText(lon+","+lat);
                //Toast.makeText(getApplicationContext(),"LAT :"+lat+"\n LON : "+lon,Toast.LENGTH_LONG).show();
            }
        }
    }

    public void pushdata2DB(){
        DatabaseReference veri = db.getReference().child("Magaza");
        firmaidtut = veri.push().getKey();
        firmaaditut = firmaadi.getText().toString();
        firmalokasyontut = firmalokasyon.getText().toString();
        kampanyasuresitut = kampanyasuresi.getText().toString();

        uploadFile(firmaidtut);

        DatabaseReference data = db.getReference().child("Magaza").child(firmaidtut);
        data.child("firmaadi").setValue(firmaaditut);
        data.child("firmalokasyon").setValue(firmalokasyontut);
        data.child("kampanyaicerik").setValue(kampanyaiceriktut);
        data.child("kampanyasuresi").setValue(kampanyasuresitut);

        Toast.makeText(getApplicationContext(),"Verileriniz Kaydedilmiştir.",Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageViewMagazaGiris.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private Boolean uploadFile(String Uid){

        boolean returned = false;

        if((BitmapDrawable) imageViewMagazaGiris.getDrawable() != null){


            imageViewMagazaGiris.setDrawingCacheEnabled(true);
            imageViewMagazaGiris.buildDrawingCache();
            Bitmap bitmap2 =((BitmapDrawable) imageViewMagazaGiris.getDrawable()).getBitmap();
            Bitmap bitmap= null;
            bitmap =  getResizedBitmap(bitmap2,500,500);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);



            byte[] data = baos.toByteArray();
            StorageReference riversRef = storageReferance.child("magazalar/"+Uid+".jpg");
            UploadTask uploadTask = riversRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

            returned = true;
        }



        return returned;

    };

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

}
