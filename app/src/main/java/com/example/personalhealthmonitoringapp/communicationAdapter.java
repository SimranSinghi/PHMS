package com.example.personalhealthmonitoringapp;

import android.os.Bundle;

import android.content.Intent;
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

public class communicationAdapter extends RecyclerView.Adapter<communicationAdapter.MyViewHolder> {
    private communicationShow activity;
    private List<communicationModel> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public communicationAdapter(communicationShow activity , List<communicationModel> mList){
        this.activity = activity;
        this.mList = mList;
    }

    public void updateData(int position){
        communicationModel item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId" , item.getId());
        bundle.putString("uApp_DocName" , item.getAppDocName());
        bundle.putString("udate_in" , item.getAppDate());
        bundle.putString("utime_in" , item.getAppTime());
        bundle.putString("uApp_Reason" , item.getAppReason());

        Intent intent = new Intent(activity , communicationActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        communicationModel item = mList.get(position);
        db.collection("Communication").document(item.getId()).delete()
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

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.itemcommunication , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull communicationAdapter.MyViewHolder holder, int position) {
        holder.AppDocName.setText(mList.get(position).getAppDocName());
        holder.AppDate.setText(mList.get(position).getAppDate());
        holder.AppTime.setText(mList.get(position).getAppTime());
        holder.AppReason.setText(mList.get(position).getAppReason());
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView AppDocName, AppDate, AppTime, AppReason;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            AppDocName = itemView.findViewById(R.id.DoctorName_text);
            AppDate = itemView.findViewById(R.id.AppointmentDate_text);
            AppTime = itemView.findViewById(R.id.AppointmentTime_text);
            AppReason = itemView.findViewById(R.id.AppointmentReason_text);
        }
    }
}





