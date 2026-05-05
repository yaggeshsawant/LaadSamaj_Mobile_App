package com.madsssoft.laadsamaj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentRegistration extends Fragment {
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
    private EditText mobileNoEditText;
    private EditText passwordText;
    private EditText emailEditText;
    private EditText locationEditText;
    private EditText whatsappEditText;
    private EditText facebookLinkEditText;
    private EditText instagramLinkEditText;
    private EditText youtubeLinkEditText;
    private EditText websiteLink;
    AppCompatButton submitButton;

    InputValidate inputvalidate = new InputValidate();
    SessionManager sessionManager = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        context = view.getContext();
        // Initialize the UI elements
        initializeViews();
        getAllCategories();
        handleAllImagePicker();

        // Set OnClickListener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to handle the submission

                if(validation()){
                    handleSubmitButtonClick();
                }
                else{
                    Toast.makeText(context, "error occured!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    private boolean validation() {
        return inputvalidate.validateName(companyNameEditText.getText().toString()) &&
                inputvalidate.validateName(ownerNameEditText.getText().toString()) &&
                inputvalidate.validateMobile(mobileNoEditText.getText().toString()) &&
                inputvalidate.validateEmail(emailEditText.getText().toString()) ;
               /* inputvalidate.validateURL(locationEditText.getText().toString()) &&
                inputvalidate.validateURL(facebookLinkEditText.getText().toString()) &&
                inputvalidate.validateURL(instagramLinkEditText.getText().toString()) &&
                inputvalidate.validateURL(youtubeLinkEditText.getText().toString()) &&
                inputvalidate.validateURL(websiteLink.getText().toString());*/


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
                    List<PojoCategory> list = response.body();

                    // Create a custom adapter using the list of PojoCategory
                    SpinnerCategoryAdapter adapter = new SpinnerCategoryAdapter(context, list);

                    // Set the adapter to the Spinner
                    categorySpinner.setAdapter(adapter);


                }
                progressDialog.dismiss();
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

        String mobileNo = mobileNoEditText.getText().toString();
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
            //Toast.makeText(context, "" + image1, Toast.LENGTH_SHORT).show();
        }
        if(bitmapImage2==null){
            image2= "";
        }else{
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            bitmapImage2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
            image2 = android.util.Base64.encodeToString(byteArrayOutputStream2.toByteArray(), Base64.DEFAULT);
           // Toast.makeText(context, "" + image2, Toast.LENGTH_SHORT).show();
        }

        progressDialog.show();


        retrofitApiInterface.insertRegistration(sessionManager.getRegId(),categoryId, ownerName, companyName, address, whatsapp, mobileNo, webLink, product, email,live_location, facebookLink, instagramLink, youtubeLink, aboutUs, image1, image2).enqueue(new Callback<PojoDefault>() {
            @Override
            public void onResponse(Call<PojoDefault> call, Response<PojoDefault> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    PojoDefault list = response.body();
                    if (list != null && list.isStatus()) {
                        // Handle a successful response with a positive status
                        Toast.makeText(context, list.getMessage(), Toast.LENGTH_SHORT).show();

                        CustomDialogThanks.showDialog(getActivity(), R.drawable.ic_right_tick, "अपने प्रतिष्ठान का रजिस्ट्रेशन करने के लिए धन्यवाद, सत्यापन होने के बाद आपका प्रतिष्ठान ऐप पर दिखने लगेगा !", "एप्लीकेशन को आगे देखने के लिए ", new CustomDialogThanks.OnContinueClickListener() {
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
                            Toast.makeText(context, list.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // Handle an unsuccessful response (HTTP status code other than 2xx)
                    if (response.code() == 400) {
                        // Handle a 400 Bad Request status
                        Toast.makeText(context, "Bad Request: " + response.message(), Toast.LENGTH_SHORT).show();

                    }

                }
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

        mobileNoEditText = view.findViewById(R.id.mobileNoText);
        //passwordText =view.findViewById(R.id.passwordText);
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

        sessionManager = new SessionManager(getActivity());
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

}