package com.example.yazlab23;

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
import com.google.firebase.auth.FirebaseAuth;

public class SifremiUnuttum extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "EmailPassword";
    EditText email;
    String emailtut;
    Button gonder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);

        email = findViewById(R.id.editTextSifremiUnuttumEmail);

        gonder = findViewById(R.id.buttonSifremiUnuttum);
        gonder.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == gonder.getId()){
            emailtut = email.getText().toString();
            reset(emailtut);
            Toast.makeText(getApplicationContext(),"Lütfen Emailini Adresinizi Kontrol Ediniz.",Toast.LENGTH_LONG).show();
        }
    }

    public void reset(String tut){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = tut;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}
