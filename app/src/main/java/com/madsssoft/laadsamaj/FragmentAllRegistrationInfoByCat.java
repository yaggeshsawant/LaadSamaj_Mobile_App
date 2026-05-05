package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAllRegistrationInfoByCat extends Fragment {
    private RecyclerView recyclerView;
    private SearchView searchInput;
    ProgressDialog progressDialog;
    private AdapterForRegistrationInfo adapter;
    RetrofitApiInterface retrofitApiInterface = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_registration_info_by_search, container, false);
        Bundle bundle = getArguments();
        recyclerView = rootView.findViewById(R.id.recycleView);
        searchInput = rootView.findViewById(R.id.searchInput);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);
        handleApi(bundle.getInt("catId"));
        return rootView;
    }
    private void handleApi(int catId) {
        //Toast.makeText(getContext(), "cat:"+catId, Toast.LENGTH_SHORT).show();
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();
        retrofitApiInterface.getRegistrationsInfoByCat(catId).enqueue(new Callback<List<PojoRegistrationInfo>>() {
            @Override
            public void onResponse(Call<List<PojoRegistrationInfo>> call, Response<List<PojoRegistrationInfo>> response) {
                 if(response.isSuccessful()){
                     //Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                     List<PojoRegistrationInfo> list = response.body();

                     RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
                     recyclerView.setLayoutManager(layoutManager);
                     adapter = new AdapterForRegistrationInfo(getActivity(), list);
                     recyclerView.setAdapter(adapter);
                 }
                 else{
                     Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                 }
                 progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoRegistrationInfo>> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
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
