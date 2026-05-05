package com.madsssoft.laadsamaj;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterForServicesTypes extends RecyclerView.Adapter<AdapterForServicesTypes.MyViewHolder> {

    private final Fragment[] nextFragmentArr;
    int[] arr_img;
    String[] arr_name;
    static Context context;
    static int serId = 0;
    int last_position = -1;

    public AdapterForServicesTypes(int[] arr_img, String[] arr_name, Fragment[] nextFragmentArr) {
        this.arr_img = arr_img;
        this.arr_name = arr_name;
        this.nextFragmentArr = nextFragmentArr;
    }

    // Inner class representing the ViewHolder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        final ImageView img;
        String serviceType;
        final TextView name;
        Fragment nextFragment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgId);
            name = itemView.findViewById(R.id.textId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeFragment(nextFragment);
                }
            });
        }

        public void changeFragment(@NonNull Fragment newFragment) {
            // Create a new fragment instance
            Bundle bundle = new Bundle();
            bundle.putString("serType", serviceType);
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
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.adapter_item_for_services_types, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.img.setImageResource(arr_img[position]);
        holder.name.setText(arr_name[position]);
        holder.nextFragment = nextFragmentArr[position];

    }

    @Override
    public int getItemCount() {
        return arr_name.length;
    }

    public void recyclerViewAnimation(View view, int position) {

        if (position > last_position) {

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);

            view.setAnimation(animation);
            last_position = position;
        }

    }
}