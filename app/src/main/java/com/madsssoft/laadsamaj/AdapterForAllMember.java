package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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

public class AdapterForAllMember extends RecyclerView.Adapter<AdapterForAllMember.MyViewHolder> {
    private final List<PojoMemberInfo> list;
    private final List<PojoMemberInfo> filteredList;
    private final Context context;


    public AdapterForAllMember(List<PojoMemberInfo> list, Context context) {
        this.list = list;
        this.filteredList = new ArrayList<>(list);
        this.context = context;
    }

    // Method to update the dataset
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<PojoMemberInfo> newList) {
        this.filteredList.clear();
        this.filteredList.addAll(newList);
        notifyDataSetChanged(); // Notify adapter that dataset has changed
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView, fatherNameId, addressId, mobileCall;
        int memberId = 0;
        Context context;
        Button mobileBtn, whatsappBtn;
        ImageButton locationImgBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.imgId);
            textView = itemView.findViewById(R.id.textId);
            fatherNameId = itemView.findViewById(R.id.fatherNameId);
            addressId = itemView.findViewById(R.id.addressId);
            mobileCall=itemView.findViewById(R.id.mobileCall);

            mobileBtn = itemView.findViewById(R.id.mobileCall);
            whatsappBtn = itemView.findViewById(R.id.whatsappBtn);
            locationImgBtn = itemView.findViewById(R.id.locationImgBtn);

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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_for_all_member, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PojoMemberInfo list = filteredList.get(position);

        holder.textView.setText(list.getnAME());
        holder.fatherNameId.setText(list.getFather());
        holder.addressId.setText(android.text.Html.fromHtml(list.getAddress()).toString().trim());
        if(list.getContact_status()=="yes"){
            holder.mobileCall.setText("private");
        }
        else{
            holder.mobileCall.setText(list.getMobile_no());
        }



        // Use setImageResource to set the image for the ImageView
        String imageUrl = list.getImage();


        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.imageView);



        holder.memberId = list.getId();

        WebServiceClass webSer = new WebServiceClass(context);
        if(!list.getMobile_no().equalsIgnoreCase("")){
            holder.mobileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.makeCall(list.getMobile_no());
                }
            });
        }

        if(!list.getWhatsapp_no().equalsIgnoreCase("")){
            holder.whatsappBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.sendWhatsAppMessage(list.getWhatsapp_no());
                }
            });
        }

        //if(!list.getLive_location().equalsIgnoreCase("")) {
        holder.locationImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                String liveLocation = list.getLive_location().toString();
                if (liveLocation != null) {
                    webSer.showLiveMapLocation(liveLocation);
                } else {
                    // Handle the case where list.getLive_location() is null
                    // For example, you could log a message or take some other action
                }

            }
        });
        //}


    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(list);
        } else {
            query = query.toLowerCase();
            for (PojoMemberInfo list : list) {
                if (list.getnAME().toLowerCase().contains(query)) {
                    filteredList.add(list);
                }
            }
        }
        notifyDataSetChanged();
    }


}