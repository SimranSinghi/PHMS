package com.example.personalhealthmonitoringapp;

import android.content.Intent;
import android.os.Bundle;
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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private vitalActivity activity;
    private List<Model> mList;
    private FirebaseFirestore db =  FirebaseFirestore.getInstance();


    public MyAdapter(vitalActivity activity,List<Model> mList){
        this.activity=activity;
        this.mList=mList;

    }
    public void updateData(int position){
        Model item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId" , item.getId());
        bundle.putString("uDrName" , item.getDName());
        bundle.putString("uBP" , item.getBP());
        bundle.putString("uHRate" , item.getHRate());
        bundle.putString("uResRate" , item.getResRate());
        bundle.putString("uchole" , item.getChol());
        bundle.putString("uBTemp" , item.getBTemp());
        bundle.putString("unotes" , item.getNotes());

        Intent intent = new Intent(activity , addVital.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }


    public void deleteData(int position){
        Model item = mList.get(position);
        db.collection("Vitals").document(item.getId()).delete()
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
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.DrName.setText(mList.get(position).getDName());
        holder.BP.setText(mList.get(position).getBP());
        holder.HRate.setText(mList.get(position).getHRate());
        holder.ResRate.setText(mList.get(position).getResRate());
        holder.chol.setText(mList.get(position).getChol());
        holder.BTemp.setText(mList.get(position).getBTemp());
        holder.notes.setText(mList.get(position).getNotes());
        holder.date.setText(mList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView DrName,BP,HRate,ResRate,chol,BTemp,notes,date;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            DrName= itemView.findViewById(R.id.textDrName);
            BP= itemView.findViewById(R.id.textbp);
            HRate= itemView.findViewById(R.id.texthrate);
            ResRate= itemView.findViewById(R.id.textresrate);
            chol= itemView.findViewById(R.id.textchol);
            BTemp= itemView.findViewById(R.id.textbtemp);
            notes= itemView.findViewById(R.id.textnotes);
            date = itemView.findViewById(R.id.textDdate);
        }
    }
}
