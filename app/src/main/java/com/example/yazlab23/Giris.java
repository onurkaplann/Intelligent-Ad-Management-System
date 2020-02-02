package com.example.yazlab23;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Giris extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";


    EditText email,sifre;
    String emailtut,sifretut;
    Button giris;

    TextView SifremiUnuttum;
    String text;
    SpannableString ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editTextGirisEmail);
        sifre = findViewById(R.id.editTextGirisSifre);

        SifremiUnuttum = findViewById(R.id.textViewGirisSifremiUnuttum);
        text = "Şifremi Unuttum";
        ss = new SpannableString(text);

        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent SifremiUnuttum = new Intent(Giris.this,SifremiUnuttum.class);
                startActivity(SifremiUnuttum);
            }
        };

        ss.setSpan(span,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SifremiUnuttum.setText(ss);
        SifremiUnuttum.setMovementMethod(LinkMovementMethod.getInstance());

        giris = findViewById(R.id.buttonGiris);
        giris.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == giris.getId()){
            emailtut = email.getText().toString();
            sifretut = sifre.getText().toString();

            if(emailtut.equals("admin") && sifretut.equals("admin")){
                Intent MagazaGiris = new Intent(Giris.this,MagazaGiris.class);
                startActivity(MagazaGiris);
            }

            else if(emailtut.equals("") && sifretut.equals("")){
                Toast.makeText(getApplicationContext(),"Email ve Şifre Giriniz.",Toast.LENGTH_LONG).show();
            }else {
                try {
                    signIn(emailtut, sifretut);
                    FirebaseUser user = mAuth.getCurrentUser();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void signIn(final String email, String password) {
        Toast.makeText(getApplicationContext(), "Giriş Yapılıyor Lütfen Bekleyiniz.", Toast.LENGTH_LONG).show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            Intent KonumveMesafe = new Intent(Giris.this,KonumveMesafe.class);
                            startActivity(KonumveMesafe);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            String Hata = ""+task.getException();
                            String[] kelime = null;
                            kelime = Hata.split(":");

                            if(kelime[1].equals(" The password is invalid or the user does not have a password.")){
                                Toast.makeText(getApplicationContext(),"Şifre Geçersiz.",Toast.LENGTH_LONG).show();
                            }

                            if(kelime[1].equals(" There is no user record corresponding to this identifier. The user may have been deleted.")){
                                Toast.makeText(getApplicationContext(),"Kullanıcı Bulunamadı.Kayıt Olabilirsiniz.",Toast.LENGTH_LONG).show();
                            }

                            if(kelime[1].equals(" The email address is badly formatted.")){
                                Toast.makeText(getApplicationContext(),"Email Formatı Hatalı.",Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                });
    }

}
