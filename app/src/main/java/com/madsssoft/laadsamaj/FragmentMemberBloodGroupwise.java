package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentMemberBloodGroupwise extends Fragment {

    Context context;
    private RecyclerView recyclerview_id;
    ProgressDialog progressDialog;
    AdapterForAllMember adapter;
    RetrofitApiInterface retrofitApiInterface;
    TextView bloodGroup_text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_member_blood_groupwise, container, false);

        context = view.getContext();
        initialization(view);
        // Retrieve the data from the arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            String data = bundle.getString("bloodGroup");
            bloodGroup_text.setText(data);
            // Use the data as needed
            loadMembers(data);
        }


        return view;
    }


    private void initialization(View view) {
        recyclerview_id = view.findViewById(R.id.recyclerView);
        /*bloodGroupSpinner = view.findViewById(R.id.bloodGroupSpinner);

        // Create and set adapter
        bloodGroupSpinner.setAdapter(new SpinnerBloodGroupAdapter(context));
*/
        bloodGroup_text=view.findViewById(R.id.bloodGropup_text);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);


    }

    private void loadMembers(String bloodGroup) {
        progressDialog.show();
        retrofitApiInterface.getMembersByBloodGroup(bloodGroup).enqueue(new Callback<List<PojoMemberInfo>>() {
            @Override
            public void onResponse(Call<List<PojoMemberInfo>> call, Response<List<PojoMemberInfo>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<PojoMemberInfo> list = response.body();

                    adapter = new AdapterForAllMember(list, getActivity());
                    recyclerview_id.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    recyclerview_id.setAdapter(adapter);
                } else {
                    Log.d("API_Response", "Response message: invalid response");
                    Toast.makeText(getActivity(), "Invalid response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PojoMemberInfo>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("API_Response", "Response message: ERROR OCCURRED");
                Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
            }
        });
    }

}