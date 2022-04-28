package com.example.personalhealthmonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class notesActivity extends AppCompatActivity implements NotesAdapter.OnItemClickListener {

    private FloatingActionButton addNoteBtn;
    private RecyclerView notesRecyclerview;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private NotesAdapter mAdapter;
    private ArrayList<Notes> notesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        addNoteBtn = findViewById(R.id.add_note_btn);
        notesRecyclerview = findViewById(R.id.notes_recycler_view);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();




        //add note activity
        addNoteBtn.setOnClickListener(view -> startActivity(new Intent(notesActivity.this, AddNoteActivity.class)));


        
        //get all notes
        getNotes();

    }

    private void getNotes() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Getting Notes");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        notesArrayList.clear();


        db.collection("notes").document(mAuth.getUid()).collection("myNotes").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
//                            Log.d(TAG, "onSuccess: LIST EMPTY");
                            progressDialog.dismiss();
                            Toast.makeText(notesActivity.this, "No Note Available", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            progressDialog.dismiss();

                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<Notes> types = queryDocumentSnapshots.toObjects(Notes.class);

                            // Add all to your list
                            notesArrayList.addAll(types);
                            Log.i("listDATA", notesArrayList.toString());
//                            Log.d(TAG, "onSuccess: " + mArrayList);
                            setNoteAdapter();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(notesActivity.this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setNoteAdapter() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        notesRecyclerview.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new NotesAdapter(notesActivity.this, notesArrayList);
        notesRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(notesActivity.this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteClick(int position) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete Note");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton(android.R.string.yes, (dialog, which) -> {
            // continue with delete
            db.collection("notes").document(Objects.requireNonNull(mAuth.getUid())).collection("myNotes")
                    .document(notesArrayList.get(position).getNoteId())
                    .delete().addOnSuccessListener(unused -> {

                        Toast.makeText(notesActivity.this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
                        getNotes();


                    }).addOnFailureListener(e -> Toast.makeText(notesActivity.this, e.toString(), Toast.LENGTH_SHORT).show());

        });
        alert.setNegativeButton(android.R.string.no, (dialog, which) -> {
            // close dialog
            dialog.cancel();
        });
        alert.show();

    }

    @Override
    public void onEditClick(int position) {

        Intent intent = new Intent(notesActivity.this, AddNoteActivity.class);
        intent.putExtra("noteId", notesArrayList.get(position).getNoteId());
        intent.putExtra("noteDescription", notesArrayList.get(position).getDescription());
        startActivity(intent);


    }
}