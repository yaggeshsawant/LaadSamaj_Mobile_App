package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentGallery extends Fragment {
    private RecyclerView recyclerview_id;
    ProgressDialog progressDialog;
    AdapterForGallery gallery_adapter = null;
    RetrofitApiInterface retrofitApiInterface = null;
    int samajId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        Bundle bundle = getArguments();
        samajId = bundle.getInt("samajId");


        handleGalleryApi(view);
        return view;

    }

    public void handleGalleryApi(View view){

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

        progressDialog.show();

        recyclerview_id = view.findViewById(R.id.recyclerView);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);
        retrofitApiInterface.getAllGalleryInfo(samajId).enqueue(new Callback<List<PojoGallery>>() {
            @Override
            public void onResponse(Call<List<PojoGallery>> call, Response<List<PojoGallery>> response) {
                if (response.isSuccessful()) {
                    List<PojoGallery> newList = response.body();
                    Log.d("API_Response", "Response Message: " + response.body());
                    // Initialize RecyclerView
                    gallery_adapter = new AdapterForGallery(newList, getActivity());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerview_id.setLayoutManager(gridLayoutManager);
                    recyclerview_id.setAdapter(gallery_adapter);

                }
                else{
                    Log.d("API_Response", "Response message: " + "invalid response");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoGallery>> call, Throwable t) {

                Log.d("API_Response", "Response message: " + "ERROR OCCURRED");
                Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });



    }


}