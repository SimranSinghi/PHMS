package com.example.personalhealthmonitoringapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    ArrayList<Notes> notes;
    Context context;

    private NotesAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(NotesAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    public NotesAdapter(Context context, ArrayList<Notes> notes) {

        this.context = context;
        this.notes = notes;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.custom_notes_layout, viewGroup, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {

        Notes values = notes.get(i);



        myViewHolder.noteDescription
                .setText(values.getDescription());




    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView noteDescription;
        Button deleteBtn, editBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            noteDescription = itemView.findViewById(R.id.note_description);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
            editBtn = itemView.findViewById(R.id.edit_btn);


            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onEditClick(position);
                        }
                    }
                }
            });

        }
    }




}

