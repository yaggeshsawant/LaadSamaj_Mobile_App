package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentChangePassword extends Fragment {
    ProgressDialog progressDialog;

    EditText newPassText ;
    AppCompatButton submitBtnView;

    SessionManager sessionManager = null;
    RetrofitApiInterface retrofitApiInterface;
    Context context ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_change_password, container, false);
        context = view.getContext();
        newPassText = view.findViewById(R.id.newPassText);
        submitBtnView = view.findViewById(R.id.submitBtnView);
        submitBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changePassword();


            }
        });




        return  view;

    }

    public void changePassword() {
        sessionManager = new SessionManager(getActivity());
        String newPass = newPassText.getText().toString();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
        progressDialog.show();

        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);
        retrofitApiInterface.changePassword(sessionManager.getRegId(),newPass).enqueue(new Callback<PojoDefault>() {
            @Override
            public void onResponse(Call<PojoDefault> call, Response<PojoDefault> response) {
                PojoDefault dto = response.body();
                //Toast.makeText(context, ""+response.body(), Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    if(dto.isStatus()){
                      //  Toast.makeText(context, dto.getMessage(), Toast.LENGTH_SHORT).show();

                        CustomDialogThanks.showDialog(getActivity(), R.drawable.ic_right_tick, "आपका पासवर्ड  सफलता पूर्वक चेंज हो चूका है !", "एप्लीकेशन को आगे देखने के लिए ", new CustomDialogThanks.OnContinueClickListener() {
                            @Override
                            public void onContinueClick() {
                                // Handle continue button click
                                sessionManager.logoutSession();
                            }
                        });

                    }

                }else{

                    Toast.makeText(context, "Error In Submit", Toast.LENGTH_SHORT).show();

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<PojoDefault> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "ERROR OCCURRED", Toast.LENGTH_SHORT).show();

            }
        });
    }

}