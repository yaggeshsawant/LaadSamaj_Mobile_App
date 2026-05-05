package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
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

public class FragmentAllSamaj extends Fragment {

    private RecyclerView recyclerView;

    ProgressDialog progressDialog;
    private AdapterForSamaj adapter;
    RetrofitApiInterface retrofitApiInterface = null;
    Fragment clickFragment = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_samaj, container, false);

        Bundle bundle = getArguments();


        String type = bundle.getString("type");

        //Toast.makeText(getContext(), "Type:"+type, Toast.LENGTH_SHORT).show();

        clickFragment = type.equalsIgnoreCase("gallery") ? new FragmentGallery() :type.equalsIgnoreCase("client") ? new FragmentClient() : new FragmentPaperCutting();



        recyclerView = rootView.findViewById(R.id.recycleView);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);


        handleApi();



        return rootView;
    }

    private void handleApi() {

           progressDialog=new ProgressDialog(getActivity());
                   progressDialog.setCancelable(false);
                   progressDialog.setMessage("Loading Please Wait...");
                   progressDialog.show();




        retrofitApiInterface.getAllSamajInfo().enqueue(new Callback<List<PojoSamaj>>() {
            @Override
            public void onResponse(Call<List<PojoSamaj>> call, Response<List<PojoSamaj>> response) {
                //progressDialog.dismiss();
                if(response.isSuccessful()) {
                    List<PojoSamaj> list = response.body();

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new AdapterForSamaj(list,getActivity(), clickFragment);
                    recyclerView.setAdapter(adapter);
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<PojoSamaj>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });



    }
}
