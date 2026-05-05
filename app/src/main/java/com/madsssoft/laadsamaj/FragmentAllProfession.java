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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentAllProfession extends Fragment {
    private RecyclerView recyclerview_id;
    ProgressDialog progressDialog;
    AdapterForAllProfession adapter = null;
    RetrofitApiInterface retrofitApiInterface = null;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_profession, container, false);

        handleProfessionApi(view);

        swipeRefreshLayout=view.findViewById(R.id.swipe_layout_id);

        //handle the refresh action
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Handle marquee setup
                handleProfessionApi(view);

                //stop the refreshing animation once data is loaded
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;

    }

    public void handleProfessionApi(View view){

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

        progressDialog.show();

        recyclerview_id = view.findViewById(R.id.recyclerView);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);
        retrofitApiInterface.getAllProfession().enqueue(new Callback<List<PojoProfession>>() {
            @Override
            public void onResponse(Call<List<PojoProfession>> call, Response<List<PojoProfession>> response) {
                if (response.isSuccessful()) {
                    List<PojoProfession> newList = response.body();
                    Log.d("API_Response", "Response Message: " + response.body());
                    // Initialize RecyclerView
                    adapter = new AdapterForAllProfession(newList, getActivity());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                    recyclerview_id.setLayoutManager(gridLayoutManager);
                    recyclerview_id.setAdapter(adapter);

                }
                else{
                    Log.d("API_Response", "Response message: " + "invalid response");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoProfession>> call, Throwable t) {

                Log.d("API_Response", "Response message: " + "ERROR OCCURRED");
                Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });



    }


}