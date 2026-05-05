package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAllBusinessCategory extends Fragment {

    private RecyclerView recyclerView;
    SearchView searchInput;

    ProgressDialog progressDialog;
    private AdapterForAllBusinessCategory adapter;
    RetrofitApiInterface retrofitApiInterface = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_business_category, container, false);


        recyclerView = rootView.findViewById(R.id.recycleView);
        searchInput = rootView.findViewById(R.id.searchInput);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);


        handleApi();



        return rootView;
    }

    private void handleApi() {

           progressDialog=new ProgressDialog(getActivity());
                   progressDialog.setCancelable(false);
                   progressDialog.setMessage("Loading Please Wait...");
                   progressDialog.show();




        retrofitApiInterface.getAllCategory().enqueue(new Callback<List<PojoCategory>>() {
            @Override
            public void onResponse(Call<List<PojoCategory>> call, Response<List<PojoCategory>> response) {
                //progressDialog.dismiss();
                if(response.isSuccessful()) {
                    List<PojoCategory> list = response.body();

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new AdapterForAllBusinessCategory(list,getActivity(),R.layout.adapter_item_for_all_business_category);
                    recyclerView.setAdapter(adapter);
                }
                else{
                    //desired toast message
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<PojoCategory>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


        searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                adapter.filter(s);
                return false;
            }
        });
    }
}
