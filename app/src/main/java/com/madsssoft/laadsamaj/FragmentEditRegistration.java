package com.madsssoft.laadsamaj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEditRegistration extends Fragment {
    int regId;
    ProgressDialog progressDialog;
    Context context;
    View view;
    Button galleryPicker1, cameraPicker1, galleryPicker2, cameraPicker2;
    ImageView previewImage1, previewImage2;
    int SELECT_GALLERY_PIC_ID1 = 201;
    int SELECT_CAMERA_PIC_ID1 = 301;
    int SELECT_GALLERY_PIC_ID2 = 202;
    int SELECT_CAMERA_PIC_ID2 = 302;
    Bitmap bitmapImage1 = null, bitmapImage2 = null;
    RetrofitApiInterface retrofitApiInterface = null;
    private Spinner categorySpinner;
    private EditText companyNameEditText;
    private EditText ownerNameEditText;
    private EditText addressEditText;
    private EditText aboutUsEditText;
    private EditText productEditText;
    private EditText emailEditText;
    private EditText locationEditText;
    private EditText whatsappEditText;
    private EditText facebookLinkEditText;
    private EditText instagramLinkEditText;
    private EditText youtubeLinkEditText;
    private EditText websiteLink;
    AppCompatButton submitButton;

    List<PojoCategory> categories = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_registration, container, false);

        Bundle bundle = getArguments();
        regId = bundle.getInt("regId");
        context = view.getContext();

        TextInputLayout mobileView = view.findViewById(R.id.mobileView);
        mobileView.setVisibility(View.GONE);


        // Initialize the UI elements
        initializeViews();
        getAllCategories();
        handleAllImagePicker();

        // Set OnClickListener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to handle the submission
                handleSubmitButtonClick();
            }
        });


        return view;
    }

    private void handleAllImagePicker() {
        galleryPicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_GALLERY_PIC_ID1);
            }
        });
        cameraPicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(SELECT_CAMERA_PIC_ID1);
            }
        });

        galleryPicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(SELECT_GALLERY_PIC_ID2);
            }
        });
        cameraPicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera(SELECT_CAMERA_PIC_ID2);
            }
        });
    }

    private void getAllCategories() {

        progressDialog.show();
        retrofitApiInterface.getAllCategory().enqueue(new Callback<List<PojoCategory>>() {
            @Override
            public void onResponse(Call<List<PojoCategory>> call, Response<List<PojoCategory>> response) {
                if (response.isSuccessful()) {
                    categories = response.body();
                    // Create a custom adapter using the list of PojoCategory
                    SpinnerCategoryAdapter adapter = new SpinnerCategoryAdapter(context, categories);

                    // Set the adapter to the Spinner
                    categorySpinner.setAdapter(adapter);

                    getRegistrationInfoById(regId);


                }

            }

            @Override
            public void onFailure(Call<List<PojoCategory>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    // Method to handle the submission
    private void handleSubmitButtonClick() {
        // Retrieve values from EditText and Spinner
        String companyName = companyNameEditText.getText().toString();
        String ownerName = ownerNameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String aboutUs = aboutUsEditText.getText().toString();
        String product = productEditText.getText().toString();

        PojoCategory selectedCategory = (PojoCategory) categorySpinner.getSelectedItem();
        int categoryId = 0;
        if (selectedCategory != null) {
            categoryId = selectedCategory.getId();
            //Toast.makeText(context, ""+categoryId, Toast.LENGTH_SHORT).show();
        } else {
        }


        String email = emailEditText.getText().toString();
        String live_location = locationEditText.getText().toString();
        String whatsapp = whatsappEditText.getText().toString();
        String facebookLink = facebookLinkEditText.getText().toString();
        String instagramLink = instagramLinkEditText.getText().toString();
        String youtubeLink = youtubeLinkEditText.getText().toString();
        String webLink = websiteLink.getText().toString();

        String image1,image2;
        if(bitmapImage1==null){
            image1 = "";
        }
        else{
            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
            bitmapImage1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
            image1 = android.util.Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
        }
        if(bitmapImage2==null){
            image2= "";
        }else{
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            bitmapImage2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
            image2 = android.util.Base64.encodeToString(byteArrayOutputStream2.toByteArray(), Base64.DEFAULT);
         }


        progressDialog.show();


        retrofitApiInterface.updateRegistrationInfo(regId,categoryId, ownerName, companyName, address, whatsapp, webLink, product, email,live_location, facebookLink, instagramLink, youtubeLink, aboutUs, image1, image2).enqueue(new Callback<PojoDefault>() {
            @Override
            public void onResponse(Call<PojoDefault> call, Response<PojoDefault> response) {
                PojoDefault dto = response.body();
                Toast.makeText(context, ""+response.body(), Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    if(dto.isStatus()){
                   // Toast.makeText(context, dto.getMessage(), Toast.LENGTH_SHORT).show();

                        CustomDialogThanks.showDialog(getActivity(), R.drawable.ic_right_tick, "आपकी प्रोफाइल अपडेट हो चुकी है !", "एप्लीकेशन को आगे देखने के लिए ", new CustomDialogThanks.OnContinueClickListener() {
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


        // Perform actions with the retrieved values


    }

    public void initializeViews() {
        galleryPicker1 = view.findViewById(R.id.galleryPicker1);
        galleryPicker2 = view.findViewById(R.id.galleryPicker2);
        cameraPicker1 = view.findViewById(R.id.cameraPicker1);
        cameraPicker2 = view.findViewById(R.id.cameraPicker2);
        previewImage1 = view.findViewById(R.id.previewImage1);
        previewImage2 = view.findViewById(R.id.previewImage2);

        companyNameEditText = view.findViewById(R.id.companyNameText);
        ownerNameEditText = view.findViewById(R.id.ownerNameText);
        addressEditText = view.findViewById(R.id.addressText);
        aboutUsEditText = view.findViewById(R.id.aboutUsText);
        productEditText = view.findViewById(R.id.productText);
        categorySpinner = view.findViewById(R.id.categorySpinner);

        emailEditText = view.findViewById(R.id.emailText);
        locationEditText = view.findViewById(R.id.locationEditText);
        whatsappEditText = view.findViewById(R.id.whatsappText);
        facebookLinkEditText = view.findViewById(R.id.facebookLinkText);
        instagramLinkEditText = view.findViewById(R.id.instagramLinkText);
        youtubeLinkEditText = view.findViewById(R.id.youtubeLinkText);
        websiteLink = view.findViewById(R.id.websiteLinkText);
        submitButton = view.findViewById(R.id.submitBtn);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);

    }

    private void openCamera(int galleryPickerId) {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            // Create the camera_intent ACTION_IMAGE_CAPTURE it will open the camera for capture the image
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Start the activity with camera_intent, and request pic id
            startActivityForResult(camera_intent, galleryPickerId);

        } else {

        // dialog box open kar dega yadi pahle se permissions na ho to
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA}, galleryPickerId);


        }


    }

    public void openGallery(int galleryPickerId) {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), galleryPickerId);

    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_GALLERY_PIC_ID1) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        bitmapImage1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // update the preview image in the layout
                    previewImage1.setImageURI(selectedImageUri);
                }
            }

            if (requestCode == SELECT_CAMERA_PIC_ID1) {
                // BitMap is data structure of image file which store the image in memory
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // Set the image in imageview for display
                if (null != photo) {
                    bitmapImage1 = (Bitmap) data.getExtras().get("data");
                    previewImage1.setImageBitmap(photo);
                }
            }

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_GALLERY_PIC_ID2) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    try {
                        bitmapImage2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // update the preview image in the layout
                    previewImage2.setImageURI(selectedImageUri);
                }
            }

            if (requestCode == SELECT_CAMERA_PIC_ID2) {
                // BitMap is data structure of image file which store the image in memory
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // Set the image in imageview for display
                if (null != photo) {
                    bitmapImage2 = (Bitmap) data.getExtras().get("data");
                    previewImage2.setImageBitmap(photo);
                }
            }
        }
    }


    private void getRegistrationInfoById(int id) {
        retrofitApiInterface.getRegistrationInfoById(id).enqueue(new Callback<PojoRegistrationInfoSingle>() {
            @Override
            public void onResponse(Call<PojoRegistrationInfoSingle> call, Response<PojoRegistrationInfoSingle> response) {
                if (response.isSuccessful()) {
                   // Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    setRegistrationInfo(response.body());
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

    private void setRegistrationInfo(PojoRegistrationInfoSingle dto) {


        companyNameEditText.setText(dto.getCompany_name());
        ownerNameEditText.setText(dto.getOwner_name());
        addressEditText.setText(dto.getAddress());
        aboutUsEditText.setText(dto.getAbout());
        productEditText.setText(dto.getProduct());

        int selectedPosition = -1;
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == dto.getCategory_id_fk()) {
                selectedPosition = i;
                break;
            }
        }

        // Toast.makeText(context, "selected cat position:"+selectedPosition+", cat id:"+dto.getCategory_id_fk(), Toast.LENGTH_SHORT).show();

        if (selectedPosition != -1) {
            categorySpinner.setSelection(selectedPosition);
        } else {
            // Handle the case where the desired category is not found in the list
        }

        emailEditText.setText(dto.getEmail());
        locationEditText.setText(dto.getLive_location());
        whatsappEditText.setText(dto.getWhatsapp_no());
        facebookLinkEditText.setText(dto.getFacebook_link());
        instagramLinkEditText.setText(dto.getInstagram_link());
        youtubeLinkEditText.setText(dto.getYoutube_link());
        websiteLink.setText(dto.getWebsite_link());

        String imageUrl1 = dto.getImage1();
        Glide.with(context)
                .load(imageUrl1)
                //.placeholder(R.drawable.about_us)
                .error(R.drawable.img_no_image).into(previewImage1);

        String imageUrl2 = dto.getImage2();
        Glide.with(context)
                .load(imageUrl2)
                //.placeholder(R.drawable.about_us)
                .error(R.drawable.img_no_image).into(previewImage2);

    }

}