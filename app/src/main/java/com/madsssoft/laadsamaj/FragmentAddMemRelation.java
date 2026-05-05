package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAddMemRelation extends Fragment {



     int member_id = 0;
    ProgressDialog progressDialog;

    RetrofitApiInterface retrofitApiInterface = null;

    private Spinner mySpinner, mySpinner2;
    private ArrayList<String> itemList;

    private Button submit;
    SessionManager sessionManager ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_mem_relation, container, false);


        mySpinner=view.findViewById(R.id.professionSpinner2);
        mySpinner2=view.findViewById(R.id.professionSpinner);
        itemList = new ArrayList<>();

        submit=view.findViewById(R.id.submitBtnId);
        Bundle bundle = getArguments();
        member_id = bundle.getInt("memberId");


        //initialization of session manager
        sessionManager = new SessionManager(getActivity());
        //Initialization of retrofitApiInterface
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");



        // Populate the item list
        populateItemList();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, itemList);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mySpinner.setAdapter(adapter);
        mySpinner2.setAdapter(adapter);


        //on the click of submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                handleSubmit();
            }

            private void handleSubmit()  {
                progressDialog.show();

                String Relation_with_relative = mySpinner.getSelectedItem().toString();
                String Relative_relation_you = mySpinner2.getSelectedItem().toString();




                            retrofitApiInterface.insertRequestForRelative(sessionManager.getRegId(),member_id,Relation_with_relative,Relative_relation_you).enqueue(new Callback<PojoDefault>() {
                    @Override


                    public void onResponse(Call<PojoDefault> call, Response<PojoDefault> response) {

                        progressDialog.dismiss();

                        if (response.isSuccessful()) {
                            PojoDefault list = response.body();
                            if (list != null && list.isStatus()) {
                                // Handle a successful response with a positive status
                                Toast.makeText(getContext(), list.getMessage(), Toast.LENGTH_SHORT).show();

                                CustomDialogThanks.showDialog(getActivity(), R.drawable.ic_right_tick, "Tआपकी द्वारा भेजी गयी रिकवेस्ट आपके रिलेटिव को भेजी जा चुकी है उनकी सहमति के बाद ये आपकी प्रोफाइल में ऐड हो जायेंगे ", "एप्लीकेशन को आगे देखने के लिए ", new CustomDialogThanks.OnContinueClickListener() {
                                    @Override
                                    public void onContinueClick() {
                                        // Handle continue button click
                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.frame_drawer_id, new FragmentHome())
                                                .addToBackStack(null)
                                                .commit();
                                    }
                                });


                            } else {
                                if (list != null) {
                                    Toast.makeText(getContext(), list.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            // Handle an unsuccessful response (HTTP status code other than 2xx)
                            if (response.code() == 400) {
                                // Handle a 400 Bad Request status
                                Toast.makeText(getContext(), "Bad Request: " + response.message(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    }


                    @Override
                    public void onFailure(Call<PojoDefault> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        return view;
    }



    private void populateItemList() {
        itemList.add("Son");
        itemList.add("Father");
        itemList.add("Mother");
        itemList.add("Brother");
        itemList.add("Sister");
        itemList.add("Daugther");
        itemList.add("Wife");
    }

}