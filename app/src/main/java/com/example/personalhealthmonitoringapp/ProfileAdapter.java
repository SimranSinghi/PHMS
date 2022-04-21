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

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {
    private ShowActivity activity;
    private List<ProfileModel> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProfileAdapter(ShowActivity activity , List<ProfileModel> mList){
        this.activity = activity;
        this.mList = mList;
    }

    public void updateData(int position){
        ProfileModel item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId" , item.getId());
        bundle.putString("uName" , item.getName());
        bundle.putString("uGender" , item.getGender());
        bundle.putString("uAge" , item.getAge());
        bundle.putString("uHeight" , item.getHeight());
        bundle.putString("uWeight" , item.getWeight());
        bundle.putString("uDocName" , item.getDoctorName());
        bundle.putString("uDocVisitDate" , item.getDoctorVisitDate());
        Intent intent = new Intent(activity , profileActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        ProfileModel item = mList.get(position);
        db.collection("Documents").document(item.getId()).delete()
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
        View v = LayoutInflater.from(activity).inflate(R.layout.itemprofile , parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Name.setText(mList.get(position).getName());
        holder.Gender.setText(mList.get(position).getGender());
        holder.Age.setText(mList.get(position).getAge());
        holder.Height.setText(mList.get(position).getHeight());
        holder.Weight.setText(mList.get(position).getWeight());
        holder.DoctorName.setText(mList.get(position).getDoctorName());
        holder.DoctorVisitDate.setText(mList.get(position).getDoctorVisitDate());

    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name , Gender , Age , Height , Weight , DoctorName , DoctorVisitDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.Name_text);
            Gender = itemView.findViewById(R.id.Gender_text);
            Age = itemView.findViewById(R.id.Age_text);
            Height = itemView.findViewById(R.id.Height_text);
            Weight = itemView.findViewById(R.id.Weight_text);
            DoctorName = itemView.findViewById(R.id.DocName_text);
            DoctorVisitDate = itemView.findViewById(R.id.DocVisitDate_text);

        }
    }
}