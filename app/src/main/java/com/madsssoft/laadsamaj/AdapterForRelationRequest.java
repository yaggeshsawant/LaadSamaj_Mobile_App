package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdapterForRelationRequest extends RecyclerView.Adapter<AdapterForRelationRequest.MyViewHolder> {
    Context context;
    List<PojoRelativeInfo> list;


    SessionManager sessionManager;

    RetrofitApiInterface retrofitApiInterface;

    ProgressDialog progressDialog;

    PojoRelativeInfo dto;

    public AdapterForRelationRequest(List<PojoRelativeInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgId;
        TextView memNameId, memRelationId;
        Button Add, Remove;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgId = itemView.findViewById(R.id.imgId);
            memNameId = itemView.findViewById(R.id.memNameId);
            memRelationId = itemView.findViewById(R.id.memRelationId);
            Add = itemView.findViewById(R.id.Addbtn);
            Remove = itemView.findViewById(R.id.Removebtn);
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_for_relation_request, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        PojoRelativeInfo dto = list.get(position);
//        holder.memNameId.setText(dto.getName());
//        holder.memRelationId.setText(dto.getRelation());

        Initialization();

        dto = list.get(position);
        holder.memNameId.setText(dto.getnAME());
        holder.memRelationId.setText(dto.getRelation_with_relative());
//        holder.memberId = Integer.parseInt(dto.getRelative_id_fk());


        Glide.with(context)
                .load(dto.getImage_formate())
                .placeholder(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .error(R.drawable.img_no_image).into(holder.imgId);

//        Glide.with(context)
//                .load(dto.getImg()) // Assuming list.getImg returns the image URL or resource ID
//                .placeholder(R.drawable.myknwlogo)
//                .error(R.drawable.no_img)
//                .into(holder.imgId);
        holder.Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAdd();
            }
        });

        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRemove();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private void Initialization() {
        retrofitApiInterface = RetrofitApiClient.getApiClient(context).create(RetrofitApiInterface.class);
        sessionManager = new SessionManager(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
    }

    private void handleAdd() {

        progressDialog.show();

        int Relative_id_fk = dto.relative_id_fk;
        String Relation_with_relative = dto.relation_with_relative;
        String Relative_relation_you = dto.relative_relation_you;

        progressDialog.dismiss();
        retrofitApiInterface.insertResponseForRequest(dto.getId(), dto.getMember_id_fk(), Relative_id_fk, Relation_with_relative, Relative_relation_you, "Yes").enqueue(new Callback<PojoDefault>() {
            @Override
            public void onResponse(Call<PojoDefault> call, Response<PojoDefault>response) {


                if (response.isSuccessful()) {
                    PojoDefault list = (PojoDefault) response.body();
                    if (list != null && list.isStatus()) {
                        // Handle a successful response with a positive status
                        Toast.makeText(context, list.getMessage(), Toast.LENGTH_SHORT).show();

                        AppCompatActivity activity = (AppCompatActivity) context;
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_drawer_id, new FragmentMemberRelation())  // R.id.frame_id is the container for your fragments
                                .addToBackStack(null)  // Add the transaction to the back stack
                                .commit();
                    } else {
                        if (list != null) {
                            Toast.makeText(context, list.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Handle an unsuccessful response (HTTP status code other than 2xx)
                    if (response.code() == 400) {
                        // Handle a 400 Bad Request status
                        Toast.makeText(context, "Bad Request: " + response.message(), Toast.LENGTH_SHORT).show();

                    }

                }
            }


            @Override
            public void onFailure(Call<PojoDefault> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "ERROR OCCURRED", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void handleRemove() {
        progressDialog.show();


        int Relative_id_fk = dto.relative_id_fk;
        String Relation_with_relative = dto.relation_with_relative;
        String Relative_relation_you = dto.relative_relation_you;

        progressDialog.dismiss();
        retrofitApiInterface.insertResponseForRequest(dto.getId(), sessionManager.getRegId(), Relative_id_fk, Relation_with_relative, Relative_relation_you, "no").enqueue(new Callback<PojoDefault>() {
            @Override
            public void onResponse(Call<PojoDefault> call, Response<PojoDefault>response) {


                if (response.isSuccessful()) {
                    PojoDefault list = (PojoDefault) response.body();
                    if (list != null && list.isStatus()) {
                        // Handle a successful response with a positive status
                        Toast.makeText(context, list.getMessage(), Toast.LENGTH_SHORT).show();

                            // Replace the current fragment with the new one
                            AppCompatActivity activity = (AppCompatActivity) context;
                            activity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frame_drawer_id, new FragmentMemberRelation())  // R.id.frame_id is the container for your fragments
                                    .addToBackStack(null)  // Add the transaction to the back stack
                                    .commit();

                    } else {
                        if (list != null) {
                            Toast.makeText(context, list.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Handle an unsuccessful response (HTTP status code other than 2xx)
                    if (response.code() == 400) {
                        // Handle a 400 Bad Request status
                        Toast.makeText(context, "Bad Request: " + response.message(), Toast.LENGTH_SHORT).show();

                    }

                }
            }


            @Override
            public void onFailure(Call<PojoDefault> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "ERROR OCCURRED", Toast.LENGTH_SHORT).show();

            }
        });


    }
}