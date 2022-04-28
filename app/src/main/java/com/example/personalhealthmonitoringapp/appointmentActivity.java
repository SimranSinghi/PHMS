package com.example.personalhealthmonitoringapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;


public class appointmentActivity extends AppCompatActivity {

    private EditText mApp_DocName, mApp_Reason, mdate_in, mtime_in;
    private Button mApp_SaveBtn, mApp_ShowBtn, mAppnotifyBtn;
    private FirebaseFirestore db;
    private String uApp_DocName, uApp_Reason, udate_in, utime_in, uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        mApp_DocName=findViewById(R.id.AppdocName);
        mdate_in=findViewById(R.id.AppointmentDate);
        mtime_in=findViewById(R.id.AppointmentTime);
        mApp_Reason=findViewById(R.id.AppointmentReason);
        mApp_SaveBtn=findViewById(R.id.AppsaveBtn);
        mApp_ShowBtn=findViewById(R.id.AppshowBtn);
        mAppnotifyBtn=findViewById(R.id.AppnotifyBtn);

        mdate_in.setInputType(InputType.TYPE_NULL);
        mtime_in.setInputType(InputType.TYPE_NULL);

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
                startActivity(new Intent(appointmentActivity.this , AppointmentShowActivity.class));
            }
        });

        mApp_SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String AppDocName = mApp_DocName.getText().toString();
                String AppDate = mdate_in.getText().toString();
                String AppTime = mtime_in.getText().toString();
                String AppReason = mApp_Reason.getText().toString();


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



        mdate_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(mdate_in);
            }
        });

        mtime_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(mtime_in);
            }
        });

        mAppnotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity1.class));
            }
        });

    }

    private void showTimeDialog(final EditText mtime_in) {
        final Calendar calendar=Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                mtime_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(appointmentActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
    }

    private void showDateDialog(final EditText mdate_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
                mdate_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(appointmentActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateToFireStore(String id, String AppDocName, String AppDate, String AppTime, String AppReason){

        db.collection("Appointment").document(id).update("AppDocName" , AppDocName , "AppDate", AppDate, "AppTime", AppTime, "AppReason" , AppReason)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(appointmentActivity.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(appointmentActivity.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(appointmentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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


            db.collection("Appointment").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(appointmentActivity.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(appointmentActivity.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}


