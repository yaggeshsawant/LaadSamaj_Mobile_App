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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterForClient extends RecyclerView.Adapter<AdapterForClient.MyViewHolder> {

    Context context;
    int last_position = -1;

    private List<PojoClient> list;

    public AdapterForClient(List<PojoClient> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_for_kary_karini, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PojoClient dto = list.get(position);
        holder.textView.setText(dto.getnAME());
        holder.designation_text.setText(dto.getPost());
        holder.id = dto.getId();

        Glide.with(context)
                .load(dto.getImage())
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.imageView);




        recyclerViewAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView,designation_text;
        private ImageView imageView;

        private int id = 0;
        private Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.nameView);
            designation_text = itemView.findViewById(R.id.designationId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // changeFragment(new EventViewFragment());
                }
            });
        }

        public void changeFragment(Fragment newFragment) {
            // Create a new fragment instance
            Bundle bundle = new Bundle();
            bundle.putString("id", "" + id);
            newFragment.setArguments(bundle);
            // Replace the current fragment with the new one
            AppCompatActivity activity = (AppCompatActivity) context;
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_drawer_id, newFragment)  // R.id.frame_id is the container for your fragments
                    .addToBackStack(null)  // Add the transaction to the back stack
                    .commit();
        }

        @Override
        public void onClick(View view) {
            //changeFragment(new FragmentOneEvent());


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