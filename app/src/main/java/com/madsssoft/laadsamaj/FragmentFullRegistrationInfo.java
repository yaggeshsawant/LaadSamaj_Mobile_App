package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFullRegistrationInfo extends Fragment {

    TextView companyNameText, ownerNameText, mobileText, addressText;
    ImageView logoRegImage,regImage, fbLink, webLink, inLink, ytLink;
    LinearLayout serContainer;

    RetrofitApiInterface retrofitApiInterface = null;

    ProgressDialog progressDialog;
    Context context;

    Button mobileBtn,whatsappBtn;

    ImageButton locationImgBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_registration_info, container, false);
        Bundle bundle = getArguments();
        initialization(view);
        handleRegistrationInfo(bundle.getInt("regId"));
        return view;
    }

    private void handleRegistrationInfo(int id) {
        progressDialog.show();
        retrofitApiInterface.getRegistrationInfoById(id).enqueue(new Callback<PojoRegistrationInfoSingle>() {
            @Override
            public void onResponse(Call<PojoRegistrationInfoSingle> call, Response<PojoRegistrationInfoSingle> response) {
                if (response.isSuccessful()) {
                    PojoRegistrationInfoSingle list = response.body();



                    setRegistrationInfo(list);


                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PojoRegistrationInfoSingle> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRegistrationInfo(PojoRegistrationInfoSingle list) {
        companyNameText.setText(list.getCompany_name());
        ownerNameText.setText(list.getOwner_name());
        mobileText.setText(list.getMobile());
        mobileBtn.setText(list.getMobile());
        addressText.setText(android.text.Html.fromHtml(list.getAddress()).toString().trim());

        String[] serArr = android.text.Html.fromHtml(list.getProduct()).toString().trim().split("@");

        // Print each element in the array
        for (String ser : serArr) {
            // Create a new TextView
            TextView textView = new TextView(context);

            // Set attributes for the TextView
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.arrow_up, 0, 0, 0);
            textView.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen.drawable_padding));
            textView.setGravity(Gravity.CENTER | Gravity.START);
            textView.setPadding((int) getResources().getDimension(R.dimen.text_padding_start),
                    (int) getResources().getDimension(R.dimen.text_padding),
                    (int) getResources().getDimension(R.dimen.text_padding),
                    (int) getResources().getDimension(R.dimen.text_padding_end));
            textView.setText(ser);
            textView.setTypeface(null, Typeface.BOLD);

            // Add the TextView to the serContainer
            serContainer.addView(textView);
        }



        String logoImageUrl = list.getImage1();
        Glide.with(context)
                .load(logoImageUrl)
                //.placeholder(R.drawable.about_us)
                .error(R.drawable.ic_default_logo_reg).into(logoRegImage);

        String regImageUrl = list.getImage2();
        Glide.with(context)
                .load(regImageUrl)
                //.placeholder(R.drawable.about_us)
                .error(R.drawable.img_no_image).into(regImage);


        WebServiceClass webSer = new WebServiceClass(context);
        if(!list.getMobile().equalsIgnoreCase("")){
            mobileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.makeCall(list.getMobile());
                }
            });
        }

        if(!list.getWhatsapp_no().equalsIgnoreCase("")){
            whatsappBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.sendWhatsAppMessage(list.getWhatsapp_no());
                }
            });
        }

        //if(!list.getLive_location().equalsIgnoreCase("")) {
            locationImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.showLiveMapLocation(list.getLive_location());
                }
            });
        //}

        if(!list.getFacebook_link().equalsIgnoreCase("")){
            fbLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.openUrl(list.getFacebook_link());
                }
            });
        }

        if(!list.getInstagram_link().equalsIgnoreCase("")){
            inLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.openUrl(list.getInstagram_link());
                }
            });
        }

        if(!list.getYoutube_link().equalsIgnoreCase("")){
            ytLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.openUrl(list.getYoutube_link());
                }
            });
        }

        if(!list.getWebsite_link().equalsIgnoreCase("")){
            webLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "Call Clicked", Toast.LENGTH_SHORT).show();
                    webSer.openUrl(list.getWebsite_link());
                }
            });
        }
    }

    private void initialization(View view) {
        context = getActivity();

        serContainer = view.findViewById(R.id.serContainer);

        mobileBtn = view.findViewById(R.id.mobileCall);
        whatsappBtn = view.findViewById(R.id.whatsappBtn);
        locationImgBtn = view.findViewById(R.id.locationImgBtn);

        fbLink = view.findViewById(R.id.fbLink);
        webLink = view.findViewById(R.id.webLink);
        inLink = view.findViewById(R.id.inLink);
        ytLink = view.findViewById(R.id.ytLink);

        companyNameText = view.findViewById(R.id.companyNameText);
        ownerNameText = view.findViewById(R.id.ownerNameText);
        mobileText = view.findViewById(R.id.mobileText);
        addressText = view.findViewById(R.id.addressText);
        regImage = view.findViewById(R.id.regImage);
        logoRegImage = view.findViewById(R.id.logoRegImage);

        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
    }
}