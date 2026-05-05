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

public class AdapterForCounts extends RecyclerView.Adapter<AdapterForCounts.MyViewHolder> {
    private final Context context;
    private final List<PojoCounts> list;

    public AdapterForCounts(Context context, List<PojoCounts> list) {
        this.context = context;
        this.list = list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgId;
//        TextView textId;
        TextView frontTextId;
        TextView numberId;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgId = itemView.findViewById(R.id.countsImgId);
           // textId = itemView.findViewById(R.id.countsTextId);
            frontTextId = itemView.findViewById(R.id.Count_textId);
            numberId = itemView.findViewById(R.id.numberId);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.adapter_for_counts, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PojoCounts item = list.get(position);
        holder.imgId.setImageResource(item.getImg());
        holder.frontTextId.setText(item.getText());
        holder.numberId.setText(String.valueOf(item.getNumber()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}