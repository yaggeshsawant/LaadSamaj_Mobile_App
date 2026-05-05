package com.madsssoft.laadsamaj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterForPlacedStudents extends RecyclerView.Adapter<AdapterForPlacedStudents.MyViewHolder> {

    private final List<PojoPlacedStudents> placedStudentsList;
    private final Context context;

    public AdapterForPlacedStudents(List<PojoPlacedStudents> placedStudentsList, Context context) {
        this.context = context;
        this.placedStudentsList = placedStudentsList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView studentImageView;
        TextView studentNameTextView;
        TextView studentCompanyName;
        TextView studentPackage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentImageView = itemView.findViewById(R.id.imgId);
            studentNameTextView = itemView.findViewById(R.id.stu_name_id);
            studentCompanyName = itemView.findViewById(R.id.stu_company_name_id);
            studentPackage = itemView.findViewById(R.id.stu_package_id);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_for_placed_student, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PojoPlacedStudents list = placedStudentsList.get(position);

        holder.studentNameTextView.setText(list.getName());
        holder.studentCompanyName.setText(list.getCompany());
        holder.studentPackage.setText(list.getMypackage());

        // Use setImageResource to set the image for the ImageView
        String imageUrl = RetrofitApiClient.getBaseUrl()+"PlacedStudentImages/"+list.getId()+"."+list.getImage_formate(); // Replace with your image URL


        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.studentImageView);



    }

    @Override
    public int getItemCount() {
        return placedStudentsList.size();
    }
}
