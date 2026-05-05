package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentAllMemberByProfession extends Fragment {
    private RecyclerView recyclerview_id;
    ProgressDialog progressDialog;
    AdapterForAllMember adapter = null;
    RetrofitApiInterface retrofitApiInterface = null;
    TextView proffesion_title_id;
    int proId = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_member_by_profession, container, false);
        Bundle bundle = getArguments();
        proId = bundle.getInt("proId");
        proffesion_title_id=view.findViewById(R.id.proffesion_title_id);


        handleMemberApi(view);
        return view;

    }

    public void handleMemberApi(View view){

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

        progressDialog.show();

        recyclerview_id = view.findViewById(R.id.recyclerView);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);
        retrofitApiInterface.getMembersByProfession(proId).enqueue(new Callback<List<PojoMemberInfo>>() {
            @Override
            public void onResponse(Call<List<PojoMemberInfo>> call, Response<List<PojoMemberInfo>> response) {
                if (response.isSuccessful()) {
                    List<PojoMemberInfo> newList = response.body();
                    Log.d("API_Response", "Response Message: " + response.body());
                    // Initialize RecyclerView
                    if(newList.isEmpty()){
                            //
                    }else{
                        proffesion_title_id.setText(newList.get(0).profession);
                    }


                    adapter = new AdapterForAllMember(newList, getActivity());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
                    recyclerview_id.setLayoutManager(gridLayoutManager);
                    recyclerview_id.setAdapter(adapter);

                }
                else{
                    Log.d("API_Response", "Response message: " + "invalid response");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoMemberInfo>> call, Throwable t) {

                Log.d("API_Response", "Response message: " + "ERROR OCCURRED");
                Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });



    }


}