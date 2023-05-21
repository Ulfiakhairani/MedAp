package com.ulfia.medapapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProjectAdapter  extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{

    ArrayList<ProjectModel> list;
    Context context;

    public ProjectAdapter(ArrayList<ProjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ProjectModel model = list.get(position);
        Picasso.get().load(model.getProductImage()).placeholder(R.drawable.upload).into(holder.itemImage);
        holder.itemDokter.setText(model.getDoctor_name());
        holder.itemSpesialis.setText(model.getSpecialist());
//        holder.itemRating.setText(model.getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SingleDoctorActivity.class);
                intent.putExtra("singleImage", model.getProductImage());
                intent.putExtra("singleDoctor", model.getDoctor_name());
                intent.putExtra("singleSpecialist", model.getSpecialist());
                intent.putExtra("singleLocation", model.getLocation());
                intent.putExtra("singlePatient", model.getPatients());
                intent.putExtra("singleRating", model.getRating());
                intent.putExtra("singleDescription", model.getDescription());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemDokter, itemRating, itemSpesialis;
        ImageView itemImage;


        public ViewHolder(View itemView) {
            super(itemView);

            itemDokter = itemView.findViewById(R.id.itemDokter);
            itemSpesialis = itemView.findViewById(R.id.itemSpesialis);
//            itemRating = itemView.findViewById(R.id.itemRating);
            itemImage = itemView.findViewById(R.id.itemImage);

        }
    }
}
