package com.example.personalhealthmonitoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.personalhealthmonitoringapp.R;

public class FAQs extends AppCompatActivity {
    Button mlogoutBtn;
    Button mHome;
    Button more;
    TextView q1, q2, q3, q4, q5,  a1, a2, a3, a4, a5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_qs);
        mlogoutBtn = findViewById(R.id.lgt);
        q1 = findViewById(R.id.q1);
        q2 = findViewById(R.id.q2);
        q3 = findViewById(R.id.q3);
        q4 = findViewById(R.id.q4);
        q5 = findViewById(R.id.q5);

        mlogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));

            }

        });
    }

}
