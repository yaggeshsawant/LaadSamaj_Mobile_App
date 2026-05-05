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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class AdapterForRegistrationInfoBySearch extends RecyclerView.Adapter<AdapterForRegistrationInfoBySearch.MyViewHolder> {
    private final Context context;
    private final List<PojoRegistrationInfo> list;
    private final List<PojoRegistrationInfo> filteredList;


    public AdapterForRegistrationInfoBySearch(Context context, List<PojoRegistrationInfo> list) {
        this.context = context;
        this.list = list;
        this.filteredList = new ArrayList<>(list);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView companyNameText;
        ImageView regImage,logoRegImage;
        TextView ownerNameText;
        Button mobileBtn,whatsappBtn;
        TextView addressText;
        TextView productText;
        ImageButton locationImgBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyNameText = itemView.findViewById(R.id.companyNameText);
            logoRegImage = itemView.findViewById(R.id.logoRegImage);
            regImage = itemView.findViewById(R.id.regImage);
            ownerNameText = itemView.findViewById(R.id.ownerNameText);
            mobileBtn = itemView.findViewById(R.id.mobileCall);
            addressText = itemView.findViewById(R.id.addressText);
            productText = itemView.findViewById(R.id.productText);
            whatsappBtn = itemView.findViewById(R.id.whatsappBtn);
            locationImgBtn = itemView.findViewById(R.id.locationImgBtn);


        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_for_registration_info, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PojoRegistrationInfo list = filteredList.get(position);

        holder.companyNameText.setText(list.getCompany_name());
        holder.ownerNameText.setText(list.getOwner_name());
        holder.mobileBtn.setText(list.getMobile());
        holder.addressText.setText(list.getAddress());
        holder.productText.setText(list.getProduct());

        // Use setImageResource to set the image for the ImageView
        String logoImageUrl = list.getImage1();

        String regImageUrl = list.getImage2();


        Glide.with(context)
                .load(logoImageUrl)
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.logoRegImage);

        Glide.with(context)
                .load(regImageUrl)
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.regImage);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new fragment instance
                FragmentFullRegistrationInfo newFragment = new FragmentFullRegistrationInfo();

                // Set arguments
                Bundle bundle = new Bundle();
                bundle.putInt("regId", list.getRegistration_id());
                newFragment.setArguments(bundle);

                // Replace the current fragment with the new one
                AppCompatActivity activity = (AppCompatActivity) context;
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_drawer_id, newFragment)  // R.id.frame_id is the container for your fragments
                        .addToBackStack(null)  // Add the transaction to the back stack
                        .commit();
            }

        });

        WebServiceClass webSer = new WebServiceClass(context);
        if(!list.getMobile().equalsIgnoreCase("")){
            holder.mobileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.makeCall(list.getMobile());
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
        return filteredList != null ? filteredList.size() : 0;
    }



}