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

import java.util.List;

public class AdapterForFrontBusinessDirectory extends RecyclerView.Adapter<AdapterForFrontBusinessDirectory.MyViewHolder> {
    private final List<PojoCategory> list;
    private final Context context;
    int layoutID = 0;

    public AdapterForFrontBusinessDirectory(List<PojoCategory> list, Context context, int layoutId) {
        this.list = list;
        this.context = context;
        this.layoutID = layoutId;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView onlineDirectoryImg;
        TextView onlineDirectoryName;
        Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            onlineDirectoryImg = itemView.findViewById(R.id.imgId);
            onlineDirectoryName = itemView.findViewById(R.id.textId);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(layoutID, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        if(position == list.size()){

            holder.onlineDirectoryName.setText("More");
            holder.onlineDirectoryImg.setImageResource(R.drawable.ic_more);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Replace the current fragment with the new one
                    AppCompatActivity activity = (AppCompatActivity) context;
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_drawer_id, new FragmentAllBusinessCategory())  // R.id.frame_id is the container for your fragments
                            .addToBackStack(null)  // Add the transaction to the back stack
                            .commit();

                }
            });



        }
        else{
            PojoCategory dto = list.get(position);

            holder.onlineDirectoryName.setText(dto.getnAME());

            // Use setImageResource to set the image for the ImageView
            String imageUrl = dto.getImage_formate();

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.img_no_image)
                    .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                    .skipMemoryCache(true).into(holder.onlineDirectoryImg);




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("catId",  dto.getId());
                    Fragment newFragment = new FragmentAllRegistrationInfoByCat();
                    newFragment.setArguments(bundle);
                    // Replace the current fragment with the new one
                    AppCompatActivity activity = (AppCompatActivity) context;
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_drawer_id, newFragment)  // R.id.frame_id is the container for your fragments
                            .addToBackStack(null)  // Add the transaction to the back stack
                            .commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() + 1 : 0;
    }




}