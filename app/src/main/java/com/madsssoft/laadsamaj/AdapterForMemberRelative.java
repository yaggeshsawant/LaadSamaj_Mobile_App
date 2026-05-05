package com.madsssoft.laadsamaj;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterForMemberRelative extends  RecyclerView.Adapter<AdapterForMemberRelative.MyViewHolder>  {
    Context context;
    List<PojoRelativeInfo> list;

    public AdapterForMemberRelative(List<PojoRelativeInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView relativeImgView;
        TextView relativeNameView, relativeRelationView,fatherNameId;
        Context context;
        int memberId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            relativeImgView = itemView.findViewById(R.id.relativeImgView);
            relativeNameView = itemView.findViewById(R.id.relativeNameView);
            relativeRelationView = itemView.findViewById(R.id.relativeRelationView);
            fatherNameId = itemView.findViewById(R.id.fatherNameId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragment(new FragmentFullMemberInfo());
                }
            });

        }

        public void changeFragment(Fragment newFragment) {
            // Create a new fragment instance
            Bundle bundle = new Bundle();
            bundle.putInt("memberId", memberId);
            newFragment.setArguments(bundle);
            // Replace the current fragment with the new one
            AppCompatActivity activity = (AppCompatActivity) context;
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_drawer_id, newFragment)  // R.id.frame_id is the container for your fragments
                    .addToBackStack(null)  // Add the transaction to the back stack
                    .commit();
        }

    }
    @NonNull
    @Override
    public AdapterForMemberRelative.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_for_relative,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForMemberRelative.MyViewHolder holder, int position) {

        PojoRelativeInfo dto = list.get(position);
        holder.relativeNameView.setText(dto.getnAME());
        holder.relativeRelationView.setText(dto.getRelation_with_relative());
        holder.memberId = (dto.getRelative_id_fk());
        holder.fatherNameId.setText(dto.getFather());


        Glide.with(context)
                .load(dto.getImage_formate())
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.relativeImgView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}