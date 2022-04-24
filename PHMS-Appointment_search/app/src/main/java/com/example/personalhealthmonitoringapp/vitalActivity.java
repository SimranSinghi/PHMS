package com.example.personalhealthmonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class vitalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Model> list;
    private static final String TAG = "FirestoreSearchActivity";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital);
        FloatingActionButton add2 = findViewById(R.id.floatingActionButton3);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MyAdapter(this , list);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper_vital(adapter));
        touchHelper.attachToRecyclerView(recyclerView);


        showData();

        EditText searchBox = findViewById(R.id.searchBox);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG,"Search has changed to " + editable);

                if (editable.toString().isEmpty()) {
                    showData();
                } else {

                    db.collection("Vitals").whereEqualTo("DrName", editable.toString()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    list.clear();
                                    for(DocumentSnapshot snapshot: task.getResult()){
                                        Model model = new Model(snapshot.getString("id") , snapshot.getString("DrName") ,snapshot.getString("date"), snapshot.getString("BP"), snapshot.getString("HRate") , snapshot.getString("ResRate"), snapshot.getString("chol") , snapshot.getString("BTemp"), snapshot.getString("notes"));
                                        list.add(model);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(vitalActivity.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),addVital.class));
            }
        });

    }
    public void showData(){
        db.collection("Vitals").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for(DocumentSnapshot snapshot: task.getResult()){
                            Model model = new Model(snapshot.getString("id") , snapshot.getString("DrName") ,snapshot.getString("date"), snapshot.getString("BP"), snapshot.getString("HRate") , snapshot.getString("ResRate"), snapshot.getString("chol") , snapshot.getString("BTemp"), snapshot.getString("notes"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(vitalActivity.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}