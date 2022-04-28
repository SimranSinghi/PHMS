package com.example.personalhealthmonitoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personalhealthmonitoringapp.R;

public class Choldinner extends AppCompatActivity {
    TextView H, TC, DS;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choldinner);
    }
}