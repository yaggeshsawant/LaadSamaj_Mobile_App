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

public class AdapterForSamaj extends RecyclerView.Adapter<AdapterForSamaj.MyViewHolder> {
    private final List<PojoSamaj> list;
    private final List<PojoSamaj> filteredList;
    private final Context context;

    Fragment clickChangeFragment = null;

    public AdapterForSamaj(List<PojoSamaj> list, Context context, Fragment clickChangeFragment) {
        this.list = list;
        this.filteredList = new ArrayList<>(list);
        this.context = context;
        this.clickChangeFragment=clickChangeFragment;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        int samajId=0;
        String address="";
        String mobile="";
        String email="";
        Fragment clickChangeFragment;
        Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFragment(clickChangeFragment);
                }
            });
        }
        public void changeFragment(Fragment newFragment) {
            // Create a new fragment instance
            Bundle bundle = new Bundle();
            bundle.putInt("samajId",  samajId);
            bundle.putString("address",  address);
            bundle.putString("mobile",  mobile);
            bundle.putString("email",  email);
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

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_for_samaj, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PojoSamaj list = filteredList.get(position);

        holder.textView.setText(list.getnAME());

        // Use setImageResource to set the image for the ImageView

        Glide.with(context)
                .load(list.getImage_formate())
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.imageView);



        holder.samajId = list.getId();
        holder.address = list.getAddress();
        holder.mobile = list.getMobile_no();
        holder.email = list.getEmail();
        holder.clickChangeFragment = clickChangeFragment;


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
            for (PojoSamaj list : list) {
                if (list.getnAME().toLowerCase().contains(query)) {
                    filteredList.add(list);
                }
            }
        }
        notifyDataSetChanged();
    }


}