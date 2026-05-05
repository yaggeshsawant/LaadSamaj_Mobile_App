package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentClient extends Fragment {

    View view;
    RetrofitApiInterface retrofitApiInterface = null;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    int samajId = 0;
    String address="";
    String mobile="";
    String email="";
    TextView emailText,mobileText,addressText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kary_karini, container, false);
        Bundle bundle = getArguments();
        samajId = bundle.getInt("samajId");
        address = bundle.getString("address");
        mobile = bundle.getString("mobile");
        email = bundle.getString("email");
        initialization();
        handleCoursesRecyclerView();
        return view;
    }

    public void initialization() {
        recyclerView = view.findViewById(R.id.recyclerView);
        addressText = view.findViewById(R.id.addressText);
        emailText = view.findViewById(R.id.emailText);
        mobileText = view.findViewById(R.id.mobileText);

        addressText.setText(address);
        emailText.setText(email);
        mobileText.setText(mobile);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");


        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);



    }

    private void handleCoursesRecyclerView() {
        progressDialog.show();

        retrofitApiInterface.getAllClient(samajId).enqueue(new Callback<List<PojoClient>>() {
            @Override
            public void onResponse(Call<List<PojoClient>> call, Response<List<PojoClient>> response) {
                if (response.isSuccessful()) {
                    List<PojoClient> newList = response.body();
                    AdapterForClient adaptor = new AdapterForClient(newList, getActivity());

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);

                    // Set the layout manager and adapter for the RecyclerView
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adaptor);

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoClient>> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


    }
}