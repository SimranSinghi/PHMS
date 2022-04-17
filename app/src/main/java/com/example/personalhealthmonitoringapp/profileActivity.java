package com.example.personalhealthmonitoringapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class profileActivity extends AppCompatActivity {

    EditText mname, mgender, mage, mheight, mweight, mdocName, mdocVisitDate;
    Button msaveBtn, mshowBtn;
    //FirebaseAuth fAuth;
    FirebaseFirestore db;
    String uId, uName, uGender , uAge, uHeight, uWeight, uDocName, uDocVisitDate;
    //  private FirebaseDatabase db = FirebaseDatabase.getInstance();
    //  private DatabaseReference root = db.getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mname = findViewById(R.id.name);
        mgender = findViewById(R.id.gender);
        mage = findViewById(R.id.age);
        mheight = findViewById(R.id.height);
        mweight = findViewById(R.id.weight);
        mdocName = findViewById(R.id.docName);
        mdocVisitDate = findViewById(R.id.docVisitDate);
        msaveBtn = findViewById(R.id.saveBtn);
        mshowBtn = findViewById(R.id.showBtn);
        //  fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            msaveBtn.setText("Update");
            uId = bundle.getString("uID");
            uName = bundle.getString("uName");
            uGender = bundle.getString("uGender");
            uAge = bundle.getString("uAge");
            uHeight = bundle.getString("uHeight");
            uWeight = bundle.getString("uWeight");
            uDocName = bundle.getString("uDocName");
            uDocVisitDate = bundle.getString("uDocVisitDate");
            mname.setText(uName);
            mgender.setText(uGender);
            mage.setText(uAge);
            mheight.setText(uHeight);
            mweight.setText(uWeight);
            mdocName.setText(uDocName);
            mdocVisitDate.setText(uDocVisitDate);


        } else {
            msaveBtn.setText("Save");
        }

        mshowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profileActivity.this, ShowActivity.class));
            }
        });

        msaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = mname.getText().toString();
                String Gender = mgender.getText().toString();
                String Age = mage.getText().toString();
                String Height = mheight.getText().toString();
                String Weight = mweight.getText().toString();
                String DoctorName = mdocName.getText().toString();
                String DoctorVisitDate = mdocVisitDate.getText().toString();

                if(DoctorVisitDate.length() > 10){
                    mdocVisitDate.setError("Incorrect format");
                    return;
                }

                //    String id = UUID.randomUUID().toString();
                //    saveToFireStore(id , Name , Gender , Age , Height , Weight , DoctorName , DoctorVisitDate);

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = uId;
                    updateToFireStore(id, Name, Gender, Age, Height, Weight, DoctorName, DoctorVisitDate);
                } else {
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, Name, Gender, Age, Height, Weight, DoctorName, DoctorVisitDate);

                }
            }
        });
    }

 /*               HashMap<String , String> userMap = new HashMap<>();

                userMap.put("First Name" , etFirstName);
                userMap.put("Last Name" , etLastName);

                root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(profileActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                    }
                }); */

        private void updateToFireStore (String id, String Name, String Gender, String Age, String
        Height, String Weight, String DoctorName, String DoctorVisitDate){

            db.collection("Documents").document(id).update("Name", Name, "Gender", Gender, "Age", Age, "Height", Height, "Weight", Weight, "DoctorName", DoctorName , "DoctorVisitDate" , DoctorVisitDate)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(profileActivity.this, "Data Updated!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(profileActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(profileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    private void saveToFireStore(String id , String Name , String Gender , String Age , String Height , String Weight , String DoctorName , String DoctorVisitDate){

        if (!Name.isEmpty() && !Gender.isEmpty() && !Age.isEmpty() && !Height.isEmpty() && !Weight.isEmpty() && !DoctorName.isEmpty() && !DoctorVisitDate.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id" , id);
            map.put("Name" , Name);
            map.put("Gender" , Gender);
            map.put("Age" , Age);
            map.put("Height" , Height);
            map.put("Weight" , Weight);
            map.put("DoctorName" , DoctorName);
            map.put("DoctorVisitDate" , DoctorVisitDate);

            db.collection("Documents").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(profileActivity.this, "Data Saved !!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(profileActivity.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty Fields not Allowed", Toast.LENGTH_SHORT).show();
    }
}

 /*       button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , ShowActivity.class));
            }
        });
*/
