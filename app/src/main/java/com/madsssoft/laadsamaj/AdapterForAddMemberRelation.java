package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterForAddMemberRelation extends RecyclerView.Adapter<AdapterForAddMemberRelation.MyviewHolder> {

    private final List<PojoMemberInfo> list;
    private static Context context = null;
    public AdapterForAddMemberRelation(List<PojoMemberInfo> list, Context context){
        this.list=list;
        this.context=context;
    }


    public static class MyviewHolder extends RecyclerView.ViewHolder{
        CircleImageView userImage;
        TextView userName, userProfession;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            userImage=itemView.findViewById(R.id.user_image_id);
            userName=itemView.findViewById(R.id.user_name_id);
            userProfession=itemView.findViewById(R.id.user_professsion_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {changeFragment(new FragmentAddMemRelation());

                }
            });
        }
        public void changeFragment(Fragment newFragment) {
          /*  // Create a new fragment instance
            Bundle bundle = new Bundle();
            bundle.putInt("memberId", memberId);
            newFragment.setArguments(bundle);*/
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
    public AdapterForAddMemberRelation.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_for_addmember_relation, parent, false);
        return new AdapterForAddMemberRelation.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterForAddMemberRelation.MyviewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.userName.setText(list.get(position).nAME);
        holder.userProfession.setText(list.get(position).profession);
        String imageUrl = list.get(position).getImage();


        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.userImage);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new fragment instance
                Bundle bundle = new Bundle();
                bundle.putInt("memberId", list.get(position).getId());
                FragmentAddMemRelation fragment= new FragmentAddMemRelation();
                fragment.setArguments(bundle);
                // Replace the current fragment with the new one
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_drawer_id, fragment)  // R.id.frame_id is the container for your fragments
                        .addToBackStack(null)  // Add the transaction to the back stack
                        .commit();
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
