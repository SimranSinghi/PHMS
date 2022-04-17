package com.example.personalhealthmonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class addVital extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText DrName;
    private EditText HRate;
    private EditText ResRate;
    private EditText chol;
    private EditText BTemp;
    private EditText notes;
    private EditText BP;
    private TextView dateText;
    private Button btn;
    private FirebaseFirestore db;
    String uid,uDrName,uBP,uHRate,uResRate,uchole,uBTemp,unotes;

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = month +"/" + day +"/"+ year;
        dateText.setText(date);
        System.out.println(date);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vital);
        DrName = findViewById(R.id.DName);
        BP=findViewById(R.id.BP);
        HRate = findViewById(R.id.HRate);
        ResRate = findViewById(R.id.rp);
        chol = findViewById(R.id.cholesterol);
        BTemp = findViewById(R.id.bTemp);
        notes = findViewById(R.id.notes);
        btn = findViewById(R.id.btn);
        db=FirebaseFirestore.getInstance();

        dateText = findViewById(R.id.dateTextID);

        findViewById(R.id.dateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            btn.setText("Update");
            uDrName = bundle.getString("uDrName");
            uid = bundle.getString("uid");
            uBP = bundle.getString("uBP");
            uHRate = bundle.getString("uHRate");
            uResRate = bundle.getString("uResRate");
            uchole = bundle.getString("uchole");
            uBTemp = bundle.getString("uBTemp");
            unotes = bundle.getString("unotes");
            DrName.setText(uDrName);
            BP.setText(uBP);
            HRate.setText(uHRate);
            ResRate.setText(uResRate);
            chol.setText(uchole);
            BTemp.setText(uBTemp);
            notes.setText(unotes);
        }else{
            btn.setText("Add");
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DrNames = DrName.getText().toString();
                String bp = BP.getText().toString();
                String HR = HRate.getText().toString();
                String RR = ResRate.getText().toString();
                String chole = chol.getText().toString();
                String bt = BTemp.getText().toString();
                String note = notes.getText().toString();
                String date=dateText.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle!=null){
                    String id = uid;
                    updateToFireStore(id,DrNames,bp,HR,RR,chole,bt,note);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id,DrNames,date,bp,HR,RR,chole,bt,note);
                }


                if(TextUtils.isEmpty(DrNames)){
                    DrName.setError("Doctor's name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(bp)){
                    BP.setError("Blood Pressure is Required.");
                    return;
                }

                if(Integer.parseInt(bp) > 200){
                    BP.setError("Blood Pressure cannot be greater than 200");
                    return;
                }
                if(TextUtils.isEmpty(chole)){
                    chol.setError("Cholesterol is Required.");
                    return;
                }
                if(Integer.parseInt(chole) > 300){
                    chol.setError("Cholesterol cannot be greater than 300");
                    return;
                }


                if(Integer.parseInt(bt) > 115){
                    BTemp.setError("Body Temperature cannot be greater than 115");
                    return;
                }
                if(Integer.parseInt(RR) > 50){
                    ResRate.setError("Respiratory Rate cannot be greater than 50");
                    return;
                }

                if(TextUtils.isEmpty(bt)){
                    BTemp.setError("Body Temperature is Required.");
                    return;
                }
                if(TextUtils.isEmpty(HR)){
                    HRate.setError("Heart rate is Required.");
                    return;
                }
                if(Integer.parseInt(HR) > 250){
                    HRate.setError("Heart Rate cannot be greater than 250");
                    return;
                }
                if(TextUtils.isEmpty(RR)){
                    ResRate.setError("Respiratory rate is Required.");
                    return;
                }

                startActivity(new Intent(getApplicationContext(),vitalActivity.class));

            }
        });
    }

    private void updateToFireStore(String id, String DrName, String BP, String HRate, String ResRate, String chol, String BTemp, String notes){
        db.collection("Vitals").document(id).update("DrName",DrName,"BP",BP,"HRate",HRate,"ResRate",ResRate,"chol",chol,"BTemp",BTemp,"notes",notes)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(addVital.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(addVital.this, "Error : " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addVital.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveToFireStore(String id, String DrName,String date,String BP, String HRate, String ResRate, String chol, String BTemp, String notes) {
        if (!DrName.isEmpty() && !BP.isEmpty() && !HRate.isEmpty() && !ResRate.isEmpty() && !chol.isEmpty() && !BTemp.isEmpty() && !notes.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("DrName", DrName);
            map.put("BP", BP);
            map.put("HRate", HRate);
            map.put("ResRate", ResRate);
            map.put("chol", chol);
            map.put("BTemp", BTemp);
            map.put("notes", notes);
            map.put("date",date);

            db.collection("Vitals").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(addVital.this,"Data Saved!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addVital.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this,"Empty Fields not allowed",Toast.LENGTH_SHORT).show();
        }
    }
}