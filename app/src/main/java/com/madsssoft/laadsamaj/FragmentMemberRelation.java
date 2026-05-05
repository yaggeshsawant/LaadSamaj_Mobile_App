package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMemberRelation extends Fragment {
    Context context;
    SessionManager sessionManager;
    RecyclerView memberRelationRecyclerViewId;
    RetrofitApiInterface retrofitApiInterface;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_relation, container, false);
        initialization(view);

        handleMemberRelationRecyclerView();
        return view;
    }

    private void initialization(View view) {
        context = view.getContext();
        memberRelationRecyclerViewId = view.findViewById(R.id.memberRelationRecyclerViewId);

        retrofitApiInterface = RetrofitApiClient.getApiClient(context).create(RetrofitApiInterface.class);
        sessionManager = new SessionManager(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

    }

    private void handleMemberRelationRecyclerView() {
        progressDialog.show();
        retrofitApiInterface.getMemberRelativeInfo(sessionManager.getRegId()+"").enqueue(new Callback<List<PojoRelativeInfo>>() {
            @Override
            public void onResponse(Call<List<PojoRelativeInfo>> call, Response<List<PojoRelativeInfo>> response) {
                if(response.isSuccessful()){
                    List<PojoRelativeInfo> list = response.body();

                    AdapterForMemberRelative adapter = new AdapterForMemberRelative(list, getActivity());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
                    memberRelationRecyclerViewId.setLayoutManager(gridLayoutManager);
                    memberRelationRecyclerViewId.setAdapter(adapter);

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoRelativeInfo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();

            }
        });

    }
}