package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
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

public class FragmentRequestRelation extends Fragment {

    Context context;
    SessionManager sessionManager;

    RetrofitApiInterface retrofitApiInterface;
    List<PojoRelativeInfo> list;
    ProgressDialog progressDialog;
    RecyclerView relationRequestRecyclerViewId;




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_relation, container, false);
        relationRequestRecyclerViewId = view.findViewById(R.id.relationRequestRecyclerViewId);

        initialization(view);

        handleRelationRequestRecyclerView();
//        handleAdd();

        return view;
    }


//    private void handleAdd() {
//        Add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                    progressDialog.show();
//
//                  PojoRelativeInfo dto = list.get();
//
//
//                    retrofitApiInterface.insertResponseForRequest(sessionManager.getRegId(),member_id,Relation_with_relative,Relative_relation_you,type).enqueue(new Callback<List<PojoDefault>>() {
//                        @Override
//                        public void onResponse(Call<List<PojoDefault>> call, Response<List<PojoDefault>> response) {
//
//                            if (response.isSuccessful()) {
//                                PojoDefault list = (PojoDefault) response.body();
//                                if (list != null && list.isStatus()) {
//                                    // Handle a successful response with a positive status
//                                    Toast.makeText(getContext(), list.getMessage(), Toast.LENGTH_SHORT).show();
//                                } else {
//                                    if (list != null) {
//                                        Toast.makeText(getContext(), list.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            } else {
//                                // Handle an unsuccessful response (HTTP status code other than 2xx)
//                                if (response.code() == 400) {
//                                    // Handle a 400 Bad Request status
//                                    Toast.makeText(getContext(), "Bad Request: " + response.message(), Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            }
//                        }
//
//
//                        @Override
//                        public void onFailure(Call<List<PojoDefault>> call, Throwable t) {
//                            progressDialog.dismiss();
//                            Toast.makeText(getContext(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//
//            }
//        });
//
//    }

    private void initialization(View view) {
        context = view.getContext();
        relationRequestRecyclerViewId = view.findViewById(R.id.relationRequestRecyclerViewId);

        retrofitApiInterface = RetrofitApiClient.getApiClient(context).create(RetrofitApiInterface.class);
        sessionManager = new SessionManager(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

    }

    private void handleRelationRequestRecyclerView(){
//        List<PojoRequest> list = new ArrayList<>();
//
//        list.add(new PojoRequest(R.drawable.no_img,"Sahil khan","brother"));
//        list.add(new PojoRequest(R.drawable.no_img,"Sahil khan","brother"));
//        list.add(new PojoRequest(R.drawable.no_img,"Sahil khan","brother"));

        progressDialog.show();
        retrofitApiInterface.getAllRequests(sessionManager.getRegId()+"").enqueue(new Callback<List<PojoRelativeInfo>>() {
            @Override
            public void onResponse(Call<List<PojoRelativeInfo>> call, Response<List<PojoRelativeInfo>> response) {
                if(response.isSuccessful()){
                   list = response.body();

                    AdapterForRelationRequest adapter = new AdapterForRelationRequest(list, getActivity());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
                    relationRequestRecyclerViewId.setLayoutManager(gridLayoutManager);
                    relationRequestRecyclerViewId.setAdapter(adapter);

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
