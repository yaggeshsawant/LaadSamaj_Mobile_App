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

import java.util.List;

public class AdapterForStuFeedback extends RecyclerView.Adapter<AdapterForStuFeedback.MyViewHolder> {

    private final List<PojoStudentFeedback> list;
    private final Context context;

    public AdapterForStuFeedback(List<PojoStudentFeedback> list, Context context) {
        this.context = context;
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgId);
            name = itemView.findViewById(R.id.nameId);
            message = itemView.findViewById(R.id.messageId);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_for_stu_feedback, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PojoStudentFeedback dto = list.get(position);

        holder.name.setText(dto.getName());
        holder.message.setText(android.text.Html.fromHtml(dto.getDescription()).toString());


        // Use setImageResource to set the image for the ImageView
        String imageUrl = RetrofitApiClient.getBaseUrl()+"PlacedStudentImages/"+dto.getId()+"."+dto.getImage_formate(); // Replace with your image URL
        Glide.with(context)
                .load(imageUrl)
                //.placeholder(R.drawable.about_us)
                .error(R.drawable.img_no_image).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
