package com.madsssoft.laadsamaj;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class AdapterForAddSlider extends PagerAdapter {

    private Context context;
    private List<PojoAdd> list;
    ImageView imageView;
    public AdapterForAddSlider(Context context, List<PojoAdd> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override

    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_item_for_login_slider, container, false);

        imageView = view.findViewById(R.id.imageView);



        Glide.with(context)
                .load(list.get(position).getImage_formate())
                .placeholder(R.drawable.img_no_image)
                .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true).into(imageView);


        container.addView(view);



        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}