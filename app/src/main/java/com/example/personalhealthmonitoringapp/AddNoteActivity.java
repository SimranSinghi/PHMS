package com.example.personalhealthmonitoringapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class AddNoteActivity extends AppCompatActivity {

    private EditText descriptionEt;
    private Button addNoteBtn;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private boolean isUpdate = false;
    private String noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        descriptionEt = findViewById(R.id.description_et);
        addNoteBtn = findViewById(R.id.add_note_btn);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Note");
            descriptionEt.setText( bundle.getString("noteDescription"));
             isUpdate = true;
             noteId = bundle.getString("noteId");
             addNoteBtn.setText("Update");


        }

        addNoteBtn.setOnClickListener(view -> {


            if (descriptionEt.getText().toString().isEmpty()) {
                descriptionEt.setError("Please enter description");
                return;
            }

            if (isUpdate){
                updateNote(noteId);
            }else {
                addNote();
            }
        });


    }

    private void updateNote(String noteId) {


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating Notes");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String key =   db.collection("notes").document().getId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", mAuth.getUid());
        map.put("description", descriptionEt.getText().toString());
        map.put("noteId", noteId);


        db.collection("notes").document(Objects.requireNonNull(mAuth.getUid())).collection("myNotes").document(noteId).update(map)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(AddNoteActivity.this,"Note Updated Successfully!!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddNoteActivity.this, notesActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(AddNoteActivity.this,"Failed "+ e.getMessage(),Toast.LENGTH_SHORT).show();
        });


    }

    private void addNote() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Adding Notes");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String key =   db.collection("notes").document().getId();
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", mAuth.getUid());
        map.put("description", descriptionEt.getText().toString());
        map.put("noteId", key);


        db.collection("notes").document(Objects.requireNonNull(mAuth.getUid())).collection("myNotes").document(key).set(map)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(AddNoteActivity.this,"Note Saved Successfully!!",Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(AddNoteActivity.this, notesActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                       finish();

                    }
                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
             Toast.makeText(AddNoteActivity.this,"Failed "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}