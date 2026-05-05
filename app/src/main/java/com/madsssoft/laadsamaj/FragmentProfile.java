package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class FragmentProfile extends Fragment {
    Context context;
    SessionManager sessionManager;
    ImageView previewImage1, previewImage2, backButton, editId;

    RetrofitApiInterface retrofitApiInterface;

    ProgressDialog progressDialog;
    View view;

    List<PojoProfession> professions = null;
    List<PojoDistrict> districts = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initialization();

        //getMemberInfoById();


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action to perform when the back button is clicked
                openProfileFragment();
            }
        });
        editId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment();
            }
        });
        return view;
    }

    private void openProfileFragment() {
        // Replace the current fragment with the profile fragment
        FragmentProfile profileFragment = new FragmentProfile();

        // Use the FragmentManager to replace the current fragment with the profileFragment
        FragmentManager fragmentManager = getParentFragmentManager(); // or getChildFragmentManager() depending on your setup

        fragmentManager.beginTransaction()
                .replace(R.id.frame_drawer_id, profileFragment) // R.id.fragment_container is the ID of the container in your layout
                .addToBackStack(null) // This allows the user to navigate back to the previous fragment by pressing the system back button
                .commit();
    }

    public void initialization() {
        context = view.getContext();
        backButton = view.findViewById(R.id.backBtnId);
        editId = view.findViewById(R.id.editId);
        retrofitApiInterface = RetrofitApiClient.getApiClient(context).create(RetrofitApiInterface.class);
        sessionManager = new SessionManager(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

    }
    private void changeFragment() {
        // Replace the current fragment with the profile fragment
        FragmentEditProfile editProfile = new FragmentEditProfile();

        // Use the FragmentManager to replace the current fragment with the profileFragment
        FragmentManager fragmentManager = getParentFragmentManager(); // or getChildFragmentManager() depending on your setup

        fragmentManager.beginTransaction()
                .replace(R.id.frame_drawer_id, editProfile) // R.id.fragment_container is the ID of the container in your layout
                .addToBackStack(null) // This allows the user to navigate back to the previous fragment by pressing the system back button
                .commit();
    }
  /*  private void getMemberInfoById() {
        retrofitApiInterface.getMemberInfoById(sessionManager.getRegId()).enqueue(new Callback<PojoMemberInfo>() {
            @Override
            public void onResponse(Call<PojoMemberInfo> call, Response<PojoMemberInfo> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    setUserInfo(response.body());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PojoMemberInfo> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    /*private void setUserInfo(PojoMemberInfo dto) {



        String imageUrl1 = dto.getImage();
        Glide.with(context)
                .load(imageUrl1)
                //.placeholder(R.drawable.about_us)
                .error(R.drawable.img_no_image).into(previewImage1);

        String imageUrl2 = dto.getImage1();
        Glide.with(context)
                .load(imageUrl2)
                //.placeholder(R.drawable.about_us)
                .error(R.drawable.img_no_image).into(previewImage2);
    }*/


}