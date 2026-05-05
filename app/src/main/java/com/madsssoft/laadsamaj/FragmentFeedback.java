package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFeedback extends Fragment {
    TextInputEditText nameId;
    TextInputEditText mobileNoId;
    TextInputEditText messageId;
    Spinner subjectId;
    Button feedback_btn_id;
    View view;
    ProgressDialog progressDialog;
    RetrofitApiInterface retrofitApiInterface = null;
    InputValidate inputValidate= new InputValidate();


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_feedback, container, false);

        //All id Initialization in this method
        allIdInitialization();

        feedback_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    handlesubmitBtn();
                }

                else{
                    Toast.makeText(getContext(), "fill all fields", Toast.LENGTH_SHORT).show();
                }
               }


        });

        return view;

    }

    private boolean validation() {
        return inputValidate.validateName(nameId.getText().toString()) &&
                inputValidate.validateMobile(mobileNoId.getText().toString()) &&
                inputValidate.validateMessage(messageId.getText().toString());
    }

    private void handlesubmitBtn() {

        String name = String.valueOf(nameId.getText());
        String mobile = String.valueOf(mobileNoId.getText());
        String subject = String.valueOf(subjectId.getSelectedItem());
        String message = String.valueOf(messageId.getText());

        progressDialog.show();

        retrofitApiInterface.insertFeedback(name, mobile, subject, message).enqueue(new Callback<PojoFeedbackResponse>() {
            @Override
            public void onResponse(Call<PojoFeedbackResponse> call, Response<PojoFeedbackResponse> response) {

                if (response.isSuccessful()) {
                    Log.d("API_Response", "Response Message: " + response.body());
                    PojoFeedbackResponse list = response.body();

                    //Toast.makeText(getActivity(), list.getMessage(), Toast.LENGTH_SHORT).show();

                    // Calling Thankyou Dialog
                    CustomDialogThanks.showDialog(getActivity(), R.drawable.ic_right_tick, "Thank You Feedback!", "We will work on it & get in touch with you", new CustomDialogThanks.OnContinueClickListener() {
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
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<PojoFeedbackResponse> call, Throwable t) {
                Log.d("API_Response", "Response message: " + "ERROR OCCURRED");
                Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_drawer_id,new FragmentLibrary()).commit();


    }

    public void allIdInitialization() {

        nameId = (TextInputEditText) view.findViewById(R.id.name_id);
        mobileNoId = (TextInputEditText) view.findViewById(R.id.mobile_id);
        subjectId = (Spinner) view.findViewById(R.id.subject_id);
        messageId = (TextInputEditText) view.findViewById(R.id.message_id);
        feedback_btn_id = (Button) view.findViewById(R.id.bottom_matrimonial_id);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);

    }

}