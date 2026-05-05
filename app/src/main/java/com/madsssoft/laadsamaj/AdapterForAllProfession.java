package com.madsssoft.laadsamaj;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class AdapterForAllProfession extends RecyclerView.Adapter<AdapterForAllProfession.MyViewHolder> {
    private final List<PojoProfession> list;
    private final List<PojoProfession> filteredList;
    private final Context context;

    public AdapterForAllProfession(List<PojoProfession> list, Context context) {
        this.list = list;
        this.filteredList = new ArrayList<>(list);
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        int proId =0;
        Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.imgId);
            textView = itemView.findViewById(R.id.textId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragment(new FragmentAllMemberByProfession());
                }
            });
        }
        public void changeFragment(Fragment newFragment) {
            // Create a new fragment instance
            Bundle bundle = new Bundle();
            bundle.putInt("proId",  proId);
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_for_all_profession, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PojoProfession list = filteredList.get(position);

        holder.textView.setText(list.getnAME());

        // Use setImageResource to set the image for the ImageView
        String imageUrl = list.getImage_formate();

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.imageView);

        holder.proId = list.getId();


    }

    @Override
    public int getItemCount() {
        return filteredList != null ? filteredList.size() : 0;
    }


    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(list);
        } else {
            query = query.toLowerCase();
            for (PojoProfession list : list) {
                if (list.getnAME().toLowerCase().contains(query)) {
                    filteredList.add(list);
                }
            }
        }
        notifyDataSetChanged();
    }


}