package com.example.personalhealthmonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    TextView resetptitle,resetpsub,resetpmail,resetpbut;
    ProgressBar prog;
    FirebaseAuth fAUTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetptitle = findViewById(R.id.rptitle);
        resetpsub = findViewById(R.id.rpsub);
        resetpmail = findViewById(R.id.rpmail);
        resetpbut = findViewById(R.id.rpbut);
        prog = findViewById(R.id.progbar);
        fAUTH = FirebaseAuth.getInstance();

        resetpbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = resetpmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                prog.setVisibility(View.VISIBLE);

                fAUTH.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "Password reset link has been sent to your email!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        prog.setVisibility(View.GONE);
                    }
                });
            }
        });



    }
}