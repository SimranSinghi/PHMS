package com.example.personalhealthmonitoringapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class medicationtouchhelper extends ItemTouchHelper.SimpleCallback {
   private MedicationAdapter adapter;
    public medicationtouchhelper(MedicationAdapter adapter) {
        super(0, ItemTouchHelper.LEFT |  ItemTouchHelper.RIGHT);
        this.adapter=adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){

        }else{
            adapter.deleteData(position);
        }
    }
}
