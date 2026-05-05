package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMatrimonial extends Fragment {
    private RecyclerView recyclerview_id;
    ProgressDialog progressDialog;
    Button maleButton, femaleButton;

    List<PojoMemberInfo> maleMatrimonial = new ArrayList<>();
    List<PojoMemberInfo> femaleMatrimonial = new ArrayList<>();
    List<PojoMemberInfo> allMembers = new ArrayList<>();
    AdapterForAllMember adapter;
    RetrofitApiInterface retrofitApiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_member_by_gender, container, false);

        initialization(view);

        // Load and display all members initially
        loadAllMembers();

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecyclerView(maleMatrimonial);
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecyclerView(femaleMatrimonial);
            }
        });

        return view;
    }

    private void initialization(View view) {
        recyclerview_id = view.findViewById(R.id.recyclerView);
        maleButton = view.findViewById(R.id.maleButton);
        femaleButton = view.findViewById(R.id.femaleButton);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);

        adapter = new AdapterForAllMember(new ArrayList<>(), getActivity());
        recyclerview_id.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerview_id.setAdapter(adapter);
    }

    private void loadAllMembers() {
        progressDialog.show();

        // Load male members
        retrofitApiInterface.getMembersByGender("Male").enqueue(new Callback<List<PojoMemberInfo>>() {
            @Override
            public void onResponse(Call<List<PojoMemberInfo>> call, Response<List<PojoMemberInfo>> response) {
                if (response.isSuccessful()) {
                    maleMatrimonial = response.body();

                    // Load female members after males
                    retrofitApiInterface.getMembersByGender("Female").enqueue(new Callback<List<PojoMemberInfo>>() {
                        @Override
                        public void onResponse(Call<List<PojoMemberInfo>> call, Response<List<PojoMemberInfo>> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                femaleMatrimonial = response.body();

                                // Merge male and female lists and update RecyclerView
                                allMembers.clear();
                                if (maleMatrimonial != null) {
                                    allMembers.addAll(maleMatrimonial);
                                }
                                if (femaleMatrimonial != null) {
                                    allMembers.addAll(femaleMatrimonial);
                                }
                                updateRecyclerView(allMembers);
                            } else {
                                Toast.makeText(getActivity(), "Invalid response", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PojoMemberInfo>> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Invalid response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PojoMemberInfo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerView(List<PojoMemberInfo> members) {
        adapter.updateData(members);
    }
}
