package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAllRegistrationOfMember extends Fragment {
    private RecyclerView recyclerView;
    Context context;
    ProgressDialog progressDialog;
    private AdapterForMemberRegistration adapter;
    RetrofitApiInterface retrofitApiInterface = null;

    SessionManager sessionManager = null;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_member_registration, container, false);
        context = rootView.getContext();
        sessionManager = new SessionManager(context);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);
        handleApi();
        return rootView;
    }
    private void handleApi() {
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();
        retrofitApiInterface.getAllMemberRegistration(sessionManager.getRegId()).enqueue(new Callback<List<PojoRegistrationInfo>>() {
            @Override
            public void onResponse(Call<List<PojoRegistrationInfo>> call, Response<List<PojoRegistrationInfo>> response) {
                 if(response.isSuccessful()){
                     List<PojoRegistrationInfo> list = response.body();
                     //Toast.makeText(context, String.valueOf(list.size()), Toast.LENGTH_SHORT).show();


                     RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
                     recyclerView.setLayoutManager(layoutManager);
                     adapter = new AdapterForMemberRegistration(getActivity(), list);
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
