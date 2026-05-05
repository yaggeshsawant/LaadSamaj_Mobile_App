package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterForMemberRegistration extends RecyclerView.Adapter<AdapterForMemberRegistration.MyViewHolder> {
    private final Context context;
    private final List<PojoRegistrationInfo> list;

    public AdapterForMemberRegistration(Context context, List<PojoRegistrationInfo> list) {
        this.context = context;
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Context context;
        TextView companyNameText, ownerNameText, addressText, productText;
        ImageView regImage,editReg;
        int regId=0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            companyNameText = itemView.findViewById(R.id.companyNameText);
            ownerNameText = itemView.findViewById(R.id.ownerNameText);
            addressText = itemView.findViewById(R.id.addressText);
            productText = itemView.findViewById(R.id.productText);
            regImage = itemView.findViewById(R.id.regImage);
            editReg = itemView.findViewById(R.id.editReg);

            // Set onClick listener for editReg ImageView
            editReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create a new fragment instance
                    FragmentEditRegistration newFragment = new FragmentEditRegistration();

                    // Set arguments
                    Bundle bundle = new Bundle();
                    bundle.putInt("regId", regId);
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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_for_member_registration_info, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        PojoRegistrationInfo dto = list.get(position);

        holder.companyNameText.setText(dto.getCompany_name());
        holder.ownerNameText.setText(dto.getOwner_name());
        holder.addressText.setText(dto.getAddress());


        holder.productText.setText(android.text.Html.fromHtml(dto.getProduct()).toString().trim().replaceAll("@",","));


        Glide.with(context)
                .load(dto.getImage1())
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.regImage);



        holder.regId = dto.getRegistration_id();



    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}