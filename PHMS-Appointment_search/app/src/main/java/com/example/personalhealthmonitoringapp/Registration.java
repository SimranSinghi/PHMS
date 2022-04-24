package com.example.personalhealthmonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-zA-Z])"  +         //any letter
                    "(?=.*[@#$%^&+=*?!])" +    //at least 1 special character
                    ".{8,}" +
                    "$");



    EditText mEmail,mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.password);
        mRegisterBtn= findViewById(R.id.RegisterBtn);
        mLoginBtn   = findViewById(R.id.LoginBtn);

        fAuth = FirebaseAuth.getInstance();


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){
                    System.out.print("Test_1");
                }
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(email.length() < 8){
                    mEmail.setError("Email Must be >= 8 Characters");

                    return;
                }

                if(password.length() < 8){
                    mPassword.setError("Password Must be >= 8 Characters");
                    return;
                }


                if(!PASSWORD_PATTERN.matcher(password).matches()){
                    mPassword.setError("Incorrect Password Format");
                    return;
                }

                Details d = new Details();
                d.setEmailId(email);
                d.setPassword(password);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        fAuth.signOut();
                                        Toast.makeText(Registration.this, "Registered Successfully. Please check your email for verification", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),Login.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        }else {
                            Toast.makeText(Registration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            };


        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });




            }
}