package com.example.personalhealthmonitoringapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MyViewHolder> {

    private medicationActivity activity;
    private List<medicationmodel> mlist;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public MedicationAdapter(medicationActivity activity, List<medicationmodel> mlist){
        this.activity=activity;
        this.mlist=mlist;
    }

    public void deleteData(int position){
        medicationmodel item = mlist.get(position);
        db.collection("Medication").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Deleted !!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void notifyRemoved(int position){
        mlist.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.medicationitem, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.DrName.setText(mlist.get(position).getDName());
        holder.Hcondition.setText(mlist.get(position).getHealth_cond());
        holder.Mpres.setText(mlist.get(position).getMed_pres());
        holder.date.setText(mlist.get(position).getDate());
        holder.medTime.setText(mlist.get(position).getMed_time());
        holder.notes.setText(mlist.get(position).getNotes());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView DrName,date,Hcondition,Mpres,medTime,notes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            DrName=itemView.findViewById(R.id.textDrsName);
            Hcondition=itemView.findViewById(R.id.textHcondition);
            Mpres=itemView.findViewById(R.id.textMpres);
            date=itemView.findViewById(R.id.textsDdate);
            medTime=itemView.findViewById(R.id.texttime);
            notes= itemView.findViewById(R.id.textmnotes);
        }
    }
}
