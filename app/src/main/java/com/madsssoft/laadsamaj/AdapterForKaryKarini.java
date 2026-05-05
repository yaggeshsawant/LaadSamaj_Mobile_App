package com.madsssoft.laadsamaj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterForKaryKarini extends RecyclerView.Adapter<AdapterForKaryKarini.MyViewHolder> {

    private final Context context;
    private final List<PojoKaryKarini> list;

    public AdapterForKaryKarini(Context context, List<PojoKaryKarini> list) {
        this.context = context;
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameView, designationId;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.nameView);
            imageView = itemView.findViewById(R.id.imageView);
            designationId = itemView.findViewById(R.id.designationId);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_for_kary_karini, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PojoKaryKarini item = list.get(position);

        holder.nameView.setText(item.getName());
        holder.imageView.setImageResource(item.getImg());
        holder.designationId.setText(item.getDesignation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
