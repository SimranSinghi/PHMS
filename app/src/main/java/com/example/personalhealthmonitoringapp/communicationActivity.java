package com.example.personalhealthmonitoringapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;



public class communicationActivity extends AppCompatActivity {


    private EditText mApp_DocName, mApp_Reason, mdate_in, mtime_in;
    private Button mApp_SaveBtn, mApp_ShowBtn;
    private FirebaseFirestore db;
    private String uApp_DocName, uApp_Reason, udate_in, utime_in, uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        mApp_DocName=findViewById(R.id.AppdocName);
        mdate_in=findViewById(R.id.AppointmentDate);
        mtime_in=findViewById(R.id.AppointmentTime);
        mApp_Reason=findViewById(R.id.AppointmentReason);
        mApp_SaveBtn=findViewById(R.id.AppsaveBtn);
        mApp_ShowBtn=findViewById(R.id.AppshowBtn);


        db= FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mApp_SaveBtn.setText("Update");
            uId = bundle.getString("uId");
            uApp_DocName = bundle.getString("uApp_DocName");
            udate_in = bundle.getString("udate_in");
            utime_in = bundle.getString("utime_in");
            uApp_Reason = bundle.getString("uApp_Reason");
            mApp_DocName.setText(uApp_DocName);
            mdate_in.setText(udate_in);
            mtime_in.setText(utime_in);
            mApp_Reason.setText(uApp_Reason);
        }else{
            mApp_SaveBtn.setText("Save");
        }

        mApp_ShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(communicationActivity.this , communicationShow.class));
            }
        });

        mApp_SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String AppDocName = mApp_DocName.getText().toString();
                String AppDate = mdate_in.getText().toString();
                String AppTime = mtime_in.getText().toString();
                String AppReason = mApp_Reason.getText().toString();


                if(TextUtils.isEmpty(AppDocName)){
                    mApp_DocName.setError("Doctor's name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(AppDate)){
                    mdate_in.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(AppTime)){
                    mtime_in.setError("Phone number is Required.");
                    return;
                }
                if(TextUtils.isEmpty(AppReason)){
                    mApp_Reason.setError("Notes is required, write N/A if nothing.");
                    return;
                }
                if(AppDocName.length()<5 || AppDocName.length()>100){
                    mApp_DocName.setError("Doctor's name Should have minimum 5 and maximum 100 Characters.");
                    return;
                }
                if(AppTime.length()!=10){
                    mtime_in.setError("Phone Number Should have exact 10 digits");
                }
                if(!AppDate.contains("@")){
                    mdate_in.setError("Enter valid Email Address");
                    return;
                }
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id, AppDocName, AppDate, AppTime, AppReason);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, AppDocName, AppDate, AppTime, AppReason);
                }

            }
        });





    }





    private void updateToFireStore(String id, String AppDocName, String AppDate, String AppTime, String AppReason){

        db.collection("Communication").document(id).update("AppDocName" , AppDocName , "AppDate", AppDate, "AppTime", AppTime, "AppReason" , AppReason)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(communicationActivity.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(communicationActivity.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(communicationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveToFireStore(String id, String AppDocName, String AppDate, String AppTime, String AppReason){

        if (!AppDocName.isEmpty() && !AppDate.isEmpty() && !AppTime.isEmpty() && !AppReason.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("AppDocName" , AppDocName);
            map.put("AppDate" , AppDate);
            map.put("AppTime" , AppTime);
            map.put("AppReason" , AppReason);


            db.collection("Communication").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(communicationActivity.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(communicationActivity.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}


