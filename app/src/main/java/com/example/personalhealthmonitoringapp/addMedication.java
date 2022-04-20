package com.example.personalhealthmonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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

public class addMedication extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText DName, Health_cond, Med_pres, Med_time,  notes;
    private Button btn;
    private TextView dateText;
    private FirebaseFirestore db;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);
        DName = findViewById(R.id.DName);
        Health_cond = findViewById(R.id.Hcondition);
        Med_pres = findViewById(R.id.Mpres);
        Med_time = findViewById(R.id.Time);
        notes = findViewById(R.id.notes);
        btn = findViewById(R.id.btn);
        dateText = findViewById(R.id.dateTextID);
        db=FirebaseFirestore.getInstance();


        findViewById(R.id.dateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String DNameStr = DName.getText().toString();
                String Hcondition = Health_cond.getText().toString();
                String NotesStr = notes.getText().toString();
                String Mpres = Med_pres.getText().toString();
                String medTime = Med_time.getText().toString();

                String dateStr = dateText.getText().toString();
                String id = UUID.randomUUID().toString();


                if(TextUtils.isEmpty(DNameStr)){
                    DName.setError("Doctor's name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(Hcondition)){
                    Health_cond.setError("Health Condition is Required.");
                    return;
                }
                if(TextUtils.isEmpty(NotesStr)){
                    notes.setError("Notes is Required. If Nothing then write N/A.");
                    return;
                }
                if(TextUtils.isEmpty(Mpres)){
                    Med_pres.setError("Medicine Prescription is Required.");
                    return;
                }
                if(TextUtils.isEmpty(medTime)){
                    Med_time.setError("Medicine Time is Required.");
                    return;
                }
                if(TextUtils.isEmpty(dateStr)){
                    dateText.setError("Starting Date is Required.");
                    return;
                }
                if(DNameStr.length()<5 || DNameStr.length()>100){
                    DName.setError("Doctor's name Should have minimum 5 and maximum 100 Characters.");
                    return;
                }
                if(Mpres.length()<5 || Mpres.length()>100){
                    Med_pres.setError("Medicine Prescription Should have minimum 5 and maximum 100 Characters.");
                    return;
                }
                if(!medTime.contains(":")){
                    Med_time.setError("Enter valid time.(With ':')");
                    return;
                }
                if(!medTime.contains("AM") && !medTime.contains("PM")){
                    Med_time.setError("Please specify AM/PM.");
                    return;
                }








                saveToFireStore(id,DNameStr,dateStr,Hcondition,NotesStr,Mpres,medTime);
                startActivity(new Intent(getApplicationContext(),medicationActivity.class));

            }
        });

    }
    private void saveToFireStore(String id, String DNameStr, String dateStr,String Hcondition, String NotesStr, String Mpres, String medTime){
        if (!DNameStr.isEmpty()  && !Hcondition.isEmpty() && !NotesStr.isEmpty() && !Mpres.isEmpty() && !medTime.isEmpty()){

            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("DrName", DNameStr);
            map.put("Hcondition", Hcondition);
            map.put("Mpres", Mpres);
            map.put("medTime", medTime);
            map.put("notes", NotesStr);
            map.put("date",dateStr);

            db.collection("Medication").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(addMedication.this,"Data Saved!!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addMedication.this,"Failed",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this,"Empty Fields not allowed",Toast.LENGTH_SHORT).show();
        }

    }
}