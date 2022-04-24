package com.example.personalhealthmonitoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.personalhealthmonitoringapp.R;

public class OsteoporosisDP extends AppCompatActivity {
    TextView Head,sh1, sh2,sh3,r1, r2, r3;
    Button f,lgout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osteoporosis_d_p);
        Head = (TextView) findViewById(R.id.hd1);
        sh1 = (TextView) findViewById(R.id.subh1);
        sh2 = (TextView) findViewById(R.id.subh2);
        sh3 = (TextView) findViewById(R.id.subh3);
        r1 = (TextView) findViewById(R.id.n1);
        r2 = (TextView) findViewById(R.id.n2);
        r3 = (TextView) findViewById(R.id.n3);
        f = (Button) findViewById(R.id.faqb);
        lgout = (Button) findViewById(R.id.lgt);

        f.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FAQs.class));
            }


        });
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Osteobreakfast.class));
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Osteolunch.class));
            }
        });

        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Osteodinner.class));
            }
        });
    }
}

