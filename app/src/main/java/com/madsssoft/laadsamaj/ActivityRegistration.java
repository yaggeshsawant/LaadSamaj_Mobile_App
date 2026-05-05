package com.madsssoft.laadsamaj;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRegistration extends AppCompatActivity {
    Context context;
    private TextInputEditText editTextDOB, editTextDOA;
    TextView loginLinkText;
    Button galleryPicker1, cameraPicker1, galleryPicker2, cameraPicker2,submitBtn;
    ImageView previewImage1, previewImage2;
    int SELECT_GALLERY_PIC_ID1 = 201;
    int SELECT_CAMERA_PIC_ID1 = 301;
    int SELECT_GALLERY_PIC_ID2 = 202;
    int SELECT_CAMERA_PIC_ID2 = 302;
    Bitmap bitmapImage1 = null, bitmapImage2 = null;
    private Calendar selectedDOA,selectedDOB;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    RadioButton radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale,radioButtonMarried,radioButtonSingle, hideContact_id,yes_radiobtn, no_radiobtn;
    Spinner professionSpinner, bloodGroupSpinner, districtSpinner;
    TextInputEditText nameText, liveLocationText, fatherNameText, motherNameText, occupationText, mobileNoText,
            passText, wifeNameText, fatherInLawText, motherInLawText, childrenText, childrenNameText, whatsappText, emailAddText, addressText, instaText, fbText, ytText;

    LinearLayout family_details_layout;
    RetrofitApiInterface retrofitApiInterface;

    ProgressDialog progressDialog ;
    List<PojoProfession> professions = null;
    List<PojoDistrict> districts = null;

    InputValidate inputvalidate = new InputValidate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = ActivityRegistration.this;
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_clr));




        initialization();
//        ValidateMobileNo();
//        validateURl();

//to disbale the family details block when single radio button is checked
        radioButtonMarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    family_details_layout.setVisibility(View.VISIBLE);
                }

            }
        });

        radioButtonSingle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    family_details_layout.setVisibility(View.GONE);
                }

            }
        });

        getAllProfession();
        getAllDistrict();
        handleAllImagePicker();
        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogForDOB();
            }
        });

        editTextDOA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogForDOA();
            }
        });

//        loginLinkText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ActivityRegistration.this, ActivityLogin.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                handleSubmit();*/

                if(validation()){
                    handleSubmit();
                }

                else{
                    Toast.makeText(ActivityRegistration.this, "Invalid input error occur", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validation() {
        return inputvalidate.validateMobile(mobileNoText.getText().toString())&&
                inputvalidate.validateName(nameText.getText().toString())&&
                inputvalidate.validateEmail(emailAddText.getText().toString())&&
                inputvalidate.validatePassword(passText.getText().toString());
               /* inputvalidate.validateURL(ytText.getText().toString())&&
                inputvalidate.validateURL(liveLocationText.getText().toString())&&
                inputvalidate.validateURL(fbText.getText().toString())&&
                inputvalidate.validateURL(instaText.getText().toString());*/

    }


    public void initialization() {
        galleryPicker1 = findViewById(R.id.galleryPicker1);
        galleryPicker2 = findViewById(R.id.galleryPicker2);
        cameraPicker1 = findViewById(R.id.cameraPicker1);
        cameraPicker2 = findViewById(R.id.cameraPicker2);
        submitBtn = findViewById(R.id.submitBtn);
        previewImage1 = findViewById(R.id.previewImage1);
        previewImage2 = findViewById(R.id.previewImage2);

        //loginLinkText = findViewById(R.id.loginLinkText);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextDOA = findViewById(R.id.editTextDOA);
        liveLocationText = findViewById(R.id.liveLocationText);
        nameText = findViewById(R.id.nameText);
        fatherNameText = findViewById(R.id.fatherNameText);
        motherNameText = findViewById(R.id.motherNameText);
        occupationText = findViewById(R.id.occupationText);

        mobileNoText = findViewById(R.id.mobileNoText);
        passText = findViewById(R.id.passText);
        wifeNameText = findViewById(R.id.wifeNameText);
        fatherInLawText = findViewById(R.id.fatherInLawText);
        motherInLawText = findViewById(R.id.motherInLawText);
        childrenText = findViewById(R.id.noOfChildrenText);
        childrenNameText = findViewById(R.id.childrenNameText);
        whatsappText = findViewById(R.id.whatsappText);
        emailAddText = findViewById(R.id.emailAddText);
        addressText = findViewById(R.id.addressText);
        instaText = findViewById(R.id.instaText);

        fbText = findViewById(R.id.fbText);
        ytText = findViewById(R.id.ytText);

        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonMarried = findViewById(R.id.radioButtonMarried);
        radioButtonSingle = findViewById(R.id.radioButtonSingle);
        professionSpinner = findViewById(R.id.professionSpinner);
        bloodGroupSpinner = findViewById(R.id.bloodGroupSpinner);
        yes_radiobtn=findViewById(R.id.yesRadioButton);
        no_radiobtn=findViewById(R.id.noRadioButton);
        family_details_layout=findViewById(R.id.family_details_layout);

        // Create and set adapter
        bloodGroupSpinner.setAdapter(new SpinnerBloodGroupAdapter(context));
        districtSpinner = findViewById(R.id.districtSpinner);
        retrofitApiInterface = RetrofitApiClient.getApiClient(getApplicationContext()).create(RetrofitApiInterface.class);


        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

    }

    private void getAllDistrict() {
        progressDialog.show();
        retrofitApiInterface.getAllDistrict().enqueue(new Callback<List<PojoDistrict>>() {
            @Override
            public void onResponse(Call<List<PojoDistrict>> call, Response<List<PojoDistrict>> response) {
                if (response.isSuccessful()) {
                    districts = response.body();



                    // Create a custom adapter using the list of PojoCategory
                    SpinnerDistrictAdapter adapter = new SpinnerDistrictAdapter(context, districts);

                    // Set the adapter to the Spinner
                    districtSpinner.setAdapter(adapter);


                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoDistrict>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    private void getAllProfession() {

        progressDialog.show();
        retrofitApiInterface.getAllProfession().enqueue(new Callback<List<PojoProfession>>() {
            @Override
            public void onResponse(Call<List<PojoProfession>> call, Response<List<PojoProfession>> response) {
                if (response.isSuccessful()) {
                    professions = response.body();

                    // Create a custom adapter using the list of PojoCategory
                    SpinnerProfessionAdapter adapter = new SpinnerProfessionAdapter(context, professions);

                    // Set the adapter to the Spinner
                    professionSpinner.setAdapter(adapter);


                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoProfession>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void handleSubmit() {
        String gender = radioButtonMale.isChecked() ? "Male" : "Female";
        String marriedStatus = radioButtonSingle.isChecked() ? "Single" : "Married";
        String hideContact = yes_radiobtn.isChecked()? "yes" : "no";

        PojoProfession SelectedPro = (PojoProfession) professionSpinner.getSelectedItem();
        int professionIdFk = 0;
        if (SelectedPro != null) {
            professionIdFk = SelectedPro.getId();
            //Toast.makeText(context, ""+categoryId, Toast.LENGTH_SHORT).show();
        } else {
        }


        PojoDistrict selectedDist = (PojoDistrict) districtSpinner.getSelectedItem();
        int districtIdFk = 0;
        if (selectedDist != null) {
            districtIdFk = selectedDist.getId();
            //Toast.makeText(context, ""+categoryId, Toast.LENGTH_SHORT).show();
        } else {
        }

        String bloodGroup = bloodGroupSpinner.getSelectedItem().toString();
        String name = nameText.getText().toString();
        String father = fatherNameText.getText().toString();
        String mother = motherNameText.getText().toString();
        String occupation = occupationText.getText().toString();
        String dob = editTextDOB.getText().toString();
        String mobileNo = mobileNoText.getText().toString();
        String whatsappNo = whatsappText.getText().toString();
        String email = emailAddText.getText().toString();
        String wifeName = wifeNameText.getText().toString();
        String fatherInLaw = fatherInLawText.getText().toString();
        String motherInLaw = motherInLawText.getText().toString();
        int numberOfChildren = Integer.parseInt(childrenText.getText().toString());
        String doa = editTextDOA.getText().toString();
        String facebookLink = fbText.getText().toString();
        String instagramLink = instaText.getText().toString();
        String youtubeLink = ytText.getText().toString();
        String address = addressText.getText().toString();
        String password = passText.getText().toString();
        String liveLocation = liveLocationText.getText().toString();
        String nameOfChildren = childrenNameText.getText().toString();

        String image1,image2;
        if(bitmapImage1==null){
            image1 = "";
        }
        else{
            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
            bitmapImage1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
            image1 = Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
           // Toast.makeText(context, "" + image1, Toast.LENGTH_SHORT).show();
        }
        if(bitmapImage2==null){
            image2= "";
        }else{
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            bitmapImage2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
            image2 = Base64.encodeToString(byteArrayOutputStream2.toByteArray(), Base64.DEFAULT);
           // Toast.makeText(context, "" + image2, Toast.LENGTH_SHORT).show();
        }


        progressDialog.show();


        retrofitApiInterface.registerUser(
                gender, marriedStatus,hideContact, professionIdFk, districtIdFk, bloodGroup, name, father, mother,
                occupation, dob, mobileNo, whatsappNo, email, wifeName, fatherInLaw, motherInLaw,
                numberOfChildren, doa, facebookLink, instagramLink, youtubeLink, address, password,
                liveLocation, nameOfChildren, image1, image2).enqueue(new Callback<PojoDefault>() {
            @Override
            public void onResponse(Call<PojoDefault> call, Response<PojoDefault> response) {
                PojoDefault list = response.body();
                //Toast.makeText(context, ""+response.body(), Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    if(list.isStatus()){
                        Toast.makeText(context, list.getMessage(), Toast.LENGTH_SHORT).show();

                        CustomDialogThanks.showDialog(ActivityRegistration.this, R.drawable.ic_right_tick, "अपना रजिस्ट्रेशन करने के लिए धन्यवाद, सत्यापन होने के बाद आपकी प्रोफाइल ऐप पर दिखने लगेगी।  उसके बाद आप लॉगिन करके ऐप देख सकते हे !", "एप्लीकेशन को आगे देखने के लिए ", new CustomDialogThanks.OnContinueClickListener() {
                            @Override
                            public void onContinueClick() {
                                // Handle continue button click
                                Intent intent = new Intent(ActivityRegistration.this, ActivityLogin.class);
                                startActivity(intent);
                                finish();
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



    private void showDatePickerDialogForDOB() {
        // Get the current date if not already set
        if (selectedDOB == null) {
            selectedDOB = Calendar.getInstance();
        }

        // Create a DatePickerDialog and set the current date as the default date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        // Update the selectedDate variable
                        selectedDOB.set(selectedYear, selectedMonth, selectedDay);

                        // Update the EditText with the selected date
                        String selectedDateStr = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        editTextDOB.setText(selectedDateStr);
                    }
                },
                selectedDOB.get(Calendar.YEAR),
                selectedDOB.get(Calendar.MONTH),
                selectedDOB.get(Calendar.DAY_OF_MONTH)
        );
        // Show the DatePickerDialog
        datePickerDialog.show();
    }
    private void showDatePickerDialogForDOA() {

        if (selectedDOA == null) {
            selectedDOA = Calendar.getInstance();
        }
        // Create a DatePickerDialog and set the current date as the default date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        // Update the EditText with the selected date
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        editTextDOA.setText(selectedDate);
                    }
                },
                selectedDOA.get(Calendar.YEAR),
                selectedDOA.get(Calendar.MONTH),
                selectedDOA.get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
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

    private void openCamera(int galleryPickerId) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Create the camera_intent ACTION_IMAGE_CAPTURE it will open the camera for capture the image
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Start the activity with camera_intent, and request pic id
            startActivityForResult(camera_intent, galleryPickerId);
        } else {
            // Dialog box to request permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, galleryPickerId);
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
                        bitmapImage1 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImageUri);
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
                        bitmapImage2 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImageUri);
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
