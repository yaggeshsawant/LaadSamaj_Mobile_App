package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentFullMemberInfo extends Fragment {
    ProgressDialog progressDialog;

    public List<PojoMemberInfo> members ;

    public  List<PojoMemberInfo> FilteredMemberList= new ArrayList<>();


    Context context;
    RetrofitApiInterface retrofitApiInterface = null;
    int memberId = 0;

    TextView nameView, applicant_id, fatherName, motherNameId, occupationId, dobId, bloodGroupId, wifeNameId,
            fatherInLawNameId, motherInLawNameId, noOfChildrenId, nameOfChildrenId,
            dateOfAnniversityId, addressId, whatsappNoId, emailId;
    ImageView imageView, fbId, instaId, ytId, imageViewBottom, backBtnId;
    Button mobileCall, whatsappBtn,add_relation;
    ImageButton locationImgBtn;

    LinearLayout addRealtion_layout,falily_layout_id;

    SessionManager sessionManager = null;


    RecyclerView addMemberRelationRecycler_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_member_info, container, false);
        Bundle bundle = getArguments();
        memberId = bundle.getInt("memberId");
        context = view.getContext();

        initialization(view);

        if(sessionManager.getRegId()==memberId){
            addRealtion_layout.setVisibility(View.VISIBLE);
        }

        handleMemberApi(view);
        AddMemberRelation();



        return view;

    }



    private void AddMemberRelation() {
        progressDialog.show();
        retrofitApiInterface.getMemberRelativeInfo(memberId+"").enqueue(new Callback<List<PojoRelativeInfo>>() {
            @Override
            public void onResponse(Call<List<PojoRelativeInfo>> call, Response<List<PojoRelativeInfo>> response) {
                if(response.isSuccessful()){
                    List<PojoRelativeInfo> list = response.body();

                    AdapterForMemberRelative adapter = new AdapterForMemberRelative(list, getActivity());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
                    addMemberRelationRecycler_id.setLayoutManager(gridLayoutManager);
                    addMemberRelationRecycler_id.setAdapter(adapter);

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
    public void changeFragment(Fragment newFragment) {
        // Create a new fragment instance
        Bundle bundle = new Bundle();
        bundle.putInt("memberId",  memberId);
        newFragment.setArguments(bundle);
        // Replace the current fragment with the new one
        AppCompatActivity activity = (AppCompatActivity) context;
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_drawer_id, newFragment)  // R.id.frame_id is the container for your fragments
                .addToBackStack(null)  // Add the transaction to the back stack
                .commit();

}
    private void initialization(View view) {
        nameView = view.findViewById(R.id.nameView);
        applicant_id=view.findViewById(R.id.applicant_id);
        fatherName = view.findViewById(R.id.fatherNameId);
        motherNameId = view.findViewById(R.id.motherNameId);
        occupationId = view.findViewById(R.id.occupationId);
        dobId = view.findViewById(R.id.dobId);
        bloodGroupId = view.findViewById(R.id.bloodGroupId);
        wifeNameId = view.findViewById(R.id.wifeNameId);
        fatherInLawNameId = view.findViewById(R.id.fatherInLawNameId);
        motherInLawNameId = view.findViewById(R.id.motherInLawNameId);
        noOfChildrenId = view.findViewById(R.id.noOfChildrenId);
        nameOfChildrenId = view.findViewById(R.id.nameOfChildrenId);
        dateOfAnniversityId = view.findViewById(R.id.dateOfAnniversityId);
        addressId = view.findViewById(R.id.addressId);
        whatsappNoId = view.findViewById(R.id.whatsappNoId);
        emailId = view.findViewById(R.id.emailId);
        fbId = view.findViewById(R.id.fbId);
        instaId = view.findViewById(R.id.instaId);
        ytId = view.findViewById(R.id.ytId);
        mobileCall = view.findViewById(R.id.mobileCall);
        whatsappBtn = view.findViewById(R.id.whatsappBtn);
        locationImgBtn = view.findViewById(R.id.locationImgBtn);


        add_relation = view.findViewById(R.id.add_relation);

        imageView = view.findViewById(R.id.imageView);
        imageViewBottom = view.findViewById(R.id.imageViewBottom);
        backBtnId = view.findViewById(R.id.backBtnId);
        addMemberRelationRecycler_id=view.findViewById(R.id.addMemberRelationRecycler_id);
        addRealtion_layout=view.findViewById(R.id.addRelation_layout);
        falily_layout_id=view.findViewById(R.id.falily_layout_id);


        //initialization of session manager
        sessionManager = new SessionManager(getActivity());
        backBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            requireActivity().onBackPressed();
            }
        });
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

    public void handleMemberApi(View view) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

        progressDialog.show();

        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);
        retrofitApiInterface.getMemberInfoById(memberId).enqueue(new Callback<PojoMemberInfo>() {
            @Override
            public void onResponse(Call<PojoMemberInfo> call, Response<PojoMemberInfo> response) {
                if (response.isSuccessful()) {
                    PojoMemberInfo dto = response.body();
                    Log.d("API_Response", "Response Message: " + response.body());

                    assert dto != null;
                    setMemberInfo(dto);


                } else {
                    Log.d("API_Response", "Response message: " + "invalid response");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PojoMemberInfo> call, Throwable t) {

                Log.d("API_Response", "Response message: " + "ERROR OCCURRED");
                Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });


    }

    private void setMemberInfo(PojoMemberInfo dto) {
        nameView.setText(dto.getnAME());
        applicant_id.setText(String.valueOf(dto.getId()));
        fatherName.setText(dto.getFather());
        motherNameId.setText(dto.getMother());
        occupationId.setText(dto.getOccupation());
        dobId.setText(formatDate(dto.getDob()));
        bloodGroupId.setText(dto.getBlood_group());
        wifeNameId.setText(dto.getWife_name());
        fatherInLawNameId.setText(dto.getFather_in_law());
        motherInLawNameId.setText(dto.getMother_in_law());
        noOfChildrenId.setText(dto.getNo_of_children());
        nameOfChildrenId.setText(dto.getName_of_children());
        dateOfAnniversityId.setText(formatDate(dto.getDoa()));
        addressId.setText(dto.getAddress());
        if (dto.getContact_status()=="yes"){
            whatsappNoId.setText("private");
        }
        else{
            whatsappNoId.setText(dto.getWhatsapp_no());
        }

        emailId.setText(dto.getEmail());
     /*   fbId.setImageResource(dto.getFacebook_link());
        instaId.setImageResource(dto.getInstagram_link());
        ytId.setImageResource(dto.getYoutube_link());*/
        if (dto.getContact_status()=="yes"){
            mobileCall.setText("private");
        }
        else{
            mobileCall.setText(dto.getMobile_no());
        }



        // Use setImageResource to set the image for the ImageView


        Glide.with(context)
                .load(dto.getImage())
                .placeholder(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .error(R.drawable.img_no_image).into(imageView);

        Glide.with(context)
                .load(dto.getImage1())
                .placeholder(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                .error(R.drawable.img_no_image).into(imageViewBottom);




        WebServiceClass webSer = new WebServiceClass(context);
        if (!dto.getMobile_no().equalsIgnoreCase("")) {
            mobileCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.makeCall(dto.getMobile_no());
                }
            });
        }




        if (!dto.getFacebook_link().equalsIgnoreCase("")) {
            fbId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.openUrl(dto.getFacebook_link());
                }
            });
        }
        if (!dto.getInstagram_link().equalsIgnoreCase("")) {
            instaId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.openUrl(dto.getInstagram_link());
                }
            });
        }
        if (!dto.getYoutube_link().equalsIgnoreCase("")) {
            ytId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.openUrl(dto.getYoutube_link());
                }
            });
        }

        if (!dto.getWhatsapp_no().equalsIgnoreCase("")) {
            whatsappBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.sendWhatsAppMessage(dto.getWhatsapp_no());
                }
            });
        }

        //if(!dto.getLive_location().equalsIgnoreCase("")) {
        locationImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                String liveLocation = dto.getLive_location().toString();
                if (liveLocation != null) {
                    webSer.showLiveMapLocation(liveLocation);
                } else {
                    // Handle the case where dto.getLive_location() is null
                    // For example, you could log a message or take some other action
                }

            }
        });



        add_relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new FragmentAddMemRelation());
            }
        });




        if(dto.getMarried_status().equalsIgnoreCase("Unmarried")){

            falily_layout_id.setVisibility(View.GONE);
        }
    }

    private String formatDate(String dob) {
        // input format from the API (yyyy-mm-dd)
        SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat outputformat = new SimpleDateFormat("dd-MM-yyyy");

        Date date = null;
        String formattedDate = "";

        try {
            // Parse the date from the API format
            date = inputformat.parse(dob);

            // Format the date to the desired format
            formattedDate = outputformat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }


}