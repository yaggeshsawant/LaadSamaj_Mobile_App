package com.madsssoft.laadsamaj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterForFrontImage extends RecyclerView.Adapter<AdapterForFrontImage.ImageViewHolder> {

    private Context context;
    private List<PojoAdd> list;

    public AdapterForFrontImage(Context context, List<PojoAdd> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_for_frontadd, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        PojoAdd dto = list.get(position);
        String imageUrl = RetrofitApiClient.getBaseUrl()+"GalleryImages/"+dto.getA_id()+"."+dto.getImage_formate(); // Replace with your image URL


        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size(); // Use 'list.size()' instead of 'imageUrls.size()'
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView); // Replace with the actual ID of your ImageView in the item layout
        }
    }
}
