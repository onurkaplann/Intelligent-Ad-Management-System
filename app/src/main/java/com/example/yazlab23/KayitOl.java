package com.example.yazlab23;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class KayitOl extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    EditText email,sifre;
    String emailtut,sifretut;
    Button kayitol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        email = findViewById(R.id.editTextKayitOlEmail);
        sifre = findViewById(R.id.editTextKayitOlSifre);

        kayitol = findViewById(R.id.buttonKayitOl);
        kayitol.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == kayitol.getId()){
            emailtut = email.getText().toString();
            sifretut = sifre.getText().toString();

            if(emailtut.equals("") && sifretut.equals("")){
                Toast.makeText(getApplicationContext(),"Email ve Şifre Giriniz.",Toast.LENGTH_LONG).show();
            }else {
                createAccount(emailtut,sifretut);
            }
        }
    }


    private void createAccount(String email, String password) {
        Toast.makeText(getApplicationContext(), "Giriş Yapılıyor Lütfen Bekleyiniz.", Toast.LENGTH_LONG).show();
        Log.d(TAG, "createAccount:" + email);

        //  showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent KonumveMesafe = new Intent(KayitOl.this,KonumveMesafe.class);
                            startActivity(KonumveMesafe);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            String Hata = ""+task.getException();

                            String[] kelime = null;
                            kelime = Hata.split(":");

                            if(kelime[1].equals(" The email address is badly formatted.")){
                                Toast.makeText(getApplicationContext(),"Email Formatı Hatalı.",Toast.LENGTH_LONG).show();
                            }

                            if(kelime[1].equals(" The given password is invalid. [ Password should be at least 6 characters ]")){
                                Toast.makeText(getApplicationContext(),"Şifre En Az 6 Haneli Olmalı.",Toast.LENGTH_LONG).show();
                            }

                            if(kelime[1].equals(" The email address is already in use by another account.")){
                                Toast.makeText(getApplicationContext(),"Bu Mail Adresi ile Başka Bir Kayıt Bulunmaktadır.",Toast.LENGTH_LONG).show();
                            }

                        }

                        // ...
                    }
                });
    }

}
