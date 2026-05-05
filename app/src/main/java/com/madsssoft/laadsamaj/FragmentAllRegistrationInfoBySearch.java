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

public class FragmentAllRegistrationInfoBySearch extends Fragment {
    private RecyclerView recyclerView;
    private SearchView searchInput;
    ProgressDialog progressDialog;
    private AdapterForRegistrationInfoBySearch adapter;
    RetrofitApiInterface retrofitApiInterface = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_registration_info_by_search, container, false);
        recyclerView = rootView.findViewById(R.id.recycleView);
        searchInput = rootView.findViewById(R.id.searchInput);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);


        searchInput.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Toast.makeText(getContext(), "searchString:"+s, Toast.LENGTH_SHORT).show();
                handleSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                return false;
            }
        });
        return rootView;
    }

    private void handleSearch(String searchString) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();
        retrofitApiInterface.getRegistrationsInfoBySearch(searchString).enqueue(new Callback<List<PojoRegistrationInfo>>() {
            @Override
            public void onResponse(Call<List<PojoRegistrationInfo>> call, Response<List<PojoRegistrationInfo>> response) {
                if (response.isSuccessful()) {
                    List<PojoRegistrationInfo> list = response.body();

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new AdapterForRegistrationInfoBySearch(getActivity(), list);
                    recyclerView.setAdapter(adapter);

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoRegistrationInfo>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }
}
