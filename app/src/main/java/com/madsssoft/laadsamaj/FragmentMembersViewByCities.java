package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMembersViewByCities extends Fragment {

    RecyclerView recyclerView;
    RetrofitApiInterface retrofitApiInterface = null;
    ProgressDialog progressDialog;
    AdapterForAllMember adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_members_view_by_cities, container, false);
        recyclerView=view.findViewById(R.id.recyclerView);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);
        // Retrieve the data from the arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            int cityId = bundle.getInt("cityId");
            handleMemberApi(cityId);
        }


        return view;
    }

    public void handleMemberApi(int districtid) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

        progressDialog.show();

        retrofitApiInterface.getMembersByCity(districtid).enqueue(new Callback<List<PojoMemberInfo>>() {
            @Override
            public void onResponse(Call<List<PojoMemberInfo>> call, Response<List<PojoMemberInfo>> response) {
                if (response.isSuccessful()) {
                    List<PojoMemberInfo> newList = response.body();
                    Log.d("API_Response", "Response Message: " + response.body());

                    adapter = new AdapterForAllMember(newList, getActivity());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);

                } else {
                    Log.d("API_Response", "Response message: invalid response");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoMemberInfo>> call, Throwable t) {
                Log.d("API_Response", "Response message: ERROR OCCURRED");
                Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}