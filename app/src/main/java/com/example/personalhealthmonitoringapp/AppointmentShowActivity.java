package com.example.personalhealthmonitoringapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AppointmentShowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private AppointmentAdapter adapter;
    private List<AppointmentModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_show);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new AppointmentAdapter(this , list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new AppointmentTouchHelper(adapter));
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
                //Log.d(TAG,"Search has changed to " + editable);

                if (editable.toString().isEmpty()) {
                    showData();
                } else {

                    db.collection("Appointment").whereEqualTo("AppDocName", editable.toString()).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    list.clear();
                                    for(DocumentSnapshot snapshot: task.getResult()){
                                        AppointmentModel model = new AppointmentModel(snapshot.getString("id") , snapshot.getString("AppDocName")  , snapshot.getString("AppDate") , snapshot.getString("AppTime") , snapshot.getString("AppReason"));
                                        list.add(model);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AppointmentShowActivity.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void showData(){

        db.collection("Appointment").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for (DocumentSnapshot snapshot : task.getResult()){

                            AppointmentModel model = new AppointmentModel(snapshot.getString("id") , snapshot.getString("AppDocName")  , snapshot.getString("AppDate") , snapshot.getString("AppTime") , snapshot.getString("AppReason"));
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AppointmentShowActivity.this, "Oops ... something went wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }
}