package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterForHome extends RecyclerView.Adapter<AdapterForHome.MyViewHolder> {
    int[] arr_image;
    String[] arr_name;
    Context context;
    int last_position = -1;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;

    public AdapterForHome(int[] arr_image, String[] arr_name, OnItemClickListener listener) {

        this.arr_image = arr_image;
        this.arr_name = arr_name;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_item_home, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.img.setImageResource(arr_image[position]);
        holder.name.setText(arr_name[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(position); // Notify the click event
            }
        });

        recyclerViewAnimation(holder.itemView, position);
    }


    @Override
    public int getItemCount() {
        return arr_image.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.imgId);
            name = (TextView) itemView.findViewById(R.id.text_view_id);
        }
    }

    public void recyclerViewAnimation(View view, int position) {

        if (position > last_position) {

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);

            view.setAnimation(animation);
            last_position = position;
        }

    }
}