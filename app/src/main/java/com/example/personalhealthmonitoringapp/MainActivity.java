package com.example.personalhealthmonitoringapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    GridLayout mainGridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainGridLayout=(GridLayout) findViewById(R.id.mainGridLayout);

        setSingleEvent(mainGridLayout);
    }
    private void setSingleEvent(GridLayout mainGridLayout) {

        for(int i=0;i<mainGridLayout.getChildCount();i++){
            CardView cardview = (CardView)mainGridLayout.getChildAt(i);
            final int finalI = i;
            cardview.setOnClickListener(v -> {
                if (finalI==0){
                    Intent intent = new Intent(MainActivity.this,profileActivity.class);
                    startActivity(intent);
                }
                else if(finalI==1){
                    Intent intent = new Intent(MainActivity.this,vitalActivity.class);
                    startActivity(intent);
                }
                else if(finalI==2){
                    Intent intent = new Intent(MainActivity.this,medicationActivity.class);
                    startActivity(intent);
                }
                else if(finalI==3){
                    Intent intent = new Intent(MainActivity.this,dietActivity.class);
                    startActivity(intent);
                }
                else if(finalI==4){
                    Intent intent = new Intent(MainActivity.this,notesActivity.class);
                    startActivity(intent);
                }
                else if(finalI==5){
                    Intent intent = new Intent(MainActivity.this,appointmentActivity.class);
                    startActivity(intent);
                }
                else if(finalI==6){
                    Intent intent = new Intent(MainActivity.this,communicationActivity.class);
                    startActivity(intent);
                }
                else if(finalI==7){
                    Intent intent = new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                }

            });
        }
    }
}