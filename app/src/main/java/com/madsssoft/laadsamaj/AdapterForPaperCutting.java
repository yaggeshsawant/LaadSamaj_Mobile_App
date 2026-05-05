package com.madsssoft.laadsamaj;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class AdapterForPaperCutting extends RecyclerView.Adapter<AdapterForPaperCutting.MyViewHolder> {
    Context context;
    int last_position = -1;
    private List<PojoPaperCutting> list;
    private List<PojoPaperCutting> filteredList;
    public AdapterForPaperCutting(List<PojoPaperCutting> list, Context context) {
        this.list = list;
        this.filteredList = new ArrayList<>(list);
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_item_gallery, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PojoPaperCutting list = filteredList.get(position);

         holder.gallery_id = list.getId();
         holder.textView.setText(list.getnAME());
        // Use setImageResource to set the image for the ImageView
        holder.imagePath=list.getImage();
        holder.imgName=list.getnAME();


        Glide.with(context)
                .load(list.getImage())
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.image);


        recyclerViewAnimation(holder.itemView, position);
    }
    @Override
    public int getItemCount() {
        return filteredList != null ? filteredList.size() : 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView textView;
        private  String imagePath,imgName;
        private int gallery_id = 0;
        private Context context;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            image = itemView.findViewById(R.id.gallery_image_id);
            textView = itemView.findViewById(R.id.gallery_text_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ImageDetailActivity.class);
                    // on below line we are passing the image path to our new activity.
                    i.putExtra("imgPath", imagePath);
                    i.putExtra("imgName", imgName);
                    // at last we are starting our activity.
                    context.startActivity(i);
                    //changeFragment(new EventViewFragment());
                }
            });
        }


        @Override
        public void onClick(View view) {
        }
    }
    public void recyclerViewAnimation(View view, int position) {
        if (position > last_position) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            view.startAnimation(animation);
            last_position = position;
        }
    }
}