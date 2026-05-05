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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterForEvents extends RecyclerView.Adapter<AdapterForEvents.MyViewHolder> {
    Context context;
    int last_position = -1;
    private List<PojoEvents> list;
    private List<PojoEvents> filteredList;
    public AdapterForEvents(List<PojoEvents> list, Context context) {
        this.list = list;
        this.filteredList = new ArrayList<>(list);
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_event_view, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PojoEvents list = filteredList.get(position);

        holder.gallery_id = list.getId();
        holder.textView.setText(list.getNAME());
        holder.description_view.setText(list.getDescription());
        holder.date_view.setText(formatDate(list.getEvent_date()));

        // Use setImageResource to set the image for the ImageView
        holder.imagePath=list.getImage_formate();
        holder.imgName=list.getNAME();

        Glide.with(context)
                .load(list.getImage_formate())
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
        private  TextView description_view;
        private TextView date_view;
        private  String imagePath,imgName;
        private int gallery_id = 0;
        private Context context;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            image = itemView.findViewById(R.id.image_value_id);
            textView = itemView.findViewById(R.id.title_value_id);
            date_view = itemView.findViewById(R.id.date_value_id);
            description_view = itemView.findViewById(R.id.description_text_id);


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


    private String formatDate(String dob) {
        // input format from the API (yyyy-mm-dd)
        SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy");

        Date date = null;
        String formattedDate = "";

        try {
            // Parse the date from the API format
            date = inputformat.parse(dob);

            // Format the date to the desired format
            formattedDate = outputformat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

}