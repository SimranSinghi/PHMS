package com.example.personalhealthmonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class medicationActivity extends AppCompatActivity {
    private MedicationAdapter adapter;
    private List<medicationmodel> list;
    private static final String TAG = "FirestoreSearchActivity";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected EditText searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);


        RecyclerView recyclerView = findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton add = findViewById(R.id.floatingActionButton);
        list = new ArrayList<>();
        adapter = new MedicationAdapter(this, list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new medicationtouchhelper(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

        showData();
        searchBox = findViewById(R.id.searchBox);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "Search has changed to " + editable);
                if (editable.toString().isEmpty()) {

                    showData();

                }else {

                    db.collection("Medication").whereEqualTo("Hcondition", editable.toString()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    list.clear();
                                    for (DocumentSnapshot snapshot : task.getResult()) {
                                        medicationmodel model = new medicationmodel(snapshot.getString("id"), snapshot.getString("DrName"),snapshot.getString("date"),snapshot.getString("Hcondition"), snapshot.getString("Mpres"), snapshot.getString("medTime"), snapshot.getString("notes"));
                                        list.add(model);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(medicationActivity.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),addVital.class));
            }
        });

    }

    public void showData() {
        db.collection("Medication").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            medicationmodel model = new medicationmodel(snapshot.getString("id"), snapshot.getString("DrName"),snapshot.getString("date"),snapshot.getString("Hcondition"), snapshot.getString("Mpres"), snapshot.getString("medTime"), snapshot.getString("notes"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(medicationActivity.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


}