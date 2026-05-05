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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEditProfile extends Fragment {
    Context context;
    SessionManager sessionManager ;
    private TextInputEditText editTextDOB, editTextDOA;
    TextView loginLinkText;
    Button galleryPicker1, cameraPicker1, galleryPicker2, cameraPicker2,submitBtn;
    ImageView previewImage1, previewImage2;
    int SELECT_GALLERY_PIC_ID1 = 201;
    int SELECT_CAMERA_PIC_ID1 = 301;
    int SELECT_GALLERY_PIC_ID2 = 202;
    int SELECT_CAMERA_PIC_ID2 = 302;
    Bitmap bitmapImage1 = null, bitmapImage2 = null;
    private Calendar selectedDate;
    LinearLayout family_details_layout;
    RadioButton radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale,radioButtonMarried,radioButtonSingle,yes_radiobtn, no_radiobtn;
    Spinner  professionSpinner, bloodGroupSpinner, districtSpinner;
    TextInputEditText nameText, liveLocationText, fatherNameText, motherNameText, occupationText, mobileNoText,
            passText, wifeNameText, fatherInLawText, motherInLawText, childrenText, childrenNameText, whatsappText, emailAddText, addressText, instaText, fbText, ytText;

    RetrofitApiInterface retrofitApiInterface;

    ProgressDialog progressDialog ;
    View view;

    List<PojoProfession> professions = null;
    List<PojoDistrict> districts = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        initialization();
        getAllProfession();


        handleAllImagePicker();
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
        editTextDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        editTextDOA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogForDateOfAnniversity();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSubmit();
            }
        });


        return view;
    }

    public void initialization() {
        context = view.getContext();
        galleryPicker1 = view.findViewById(R.id.galleryPicker1);
        galleryPicker2 = view.findViewById(R.id.galleryPicker2);
        cameraPicker1 = view.findViewById(R.id.cameraPicker1);
        cameraPicker2 = view.findViewById(R.id.cameraPicker2);
        submitBtn = view.findViewById(R.id.submitBtn);
        previewImage1 = view.findViewById(R.id.previewImage1);
        previewImage2 = view.findViewById(R.id.previewImage2);


        editTextDOB = view.findViewById(R.id.editTextDOB);
        editTextDOA = view.findViewById(R.id.editTextDOA);
        liveLocationText = view.findViewById(R.id.liveLocationText);
        nameText = view.findViewById(R.id.nameText);
        fatherNameText = view.findViewById(R.id.fatherNameText);
        motherNameText = view.findViewById(R.id.motherNameText);
        occupationText = view.findViewById(R.id.occupationText);

//        mobileNoText = view.findViewById(R.id.mobileNoText);
//        passText = view.findViewById(R.id.passText);
        wifeNameText = view.findViewById(R.id.wifeNameText);
        fatherInLawText = view.findViewById(R.id.fatherInLawText);
        motherInLawText = view.findViewById(R.id.motherInLawText);
        childrenText = view.findViewById(R.id.noOfChildrenText);
        childrenNameText = view.findViewById(R.id.childrenNameText);
        whatsappText = view.findViewById(R.id.whatsappText);
        emailAddText = view.findViewById(R.id.emailAddText);
        addressText = view.findViewById(R.id.addressText);
        instaText = view.findViewById(R.id.instaText);

        fbText = view.findViewById(R.id.fbText);
        ytText = view.findViewById(R.id.ytText);
        radioButtonMale = view.findViewById(R.id.radioButtonMale);
        radioButtonFemale = view.findViewById(R.id.radioButtonFemale);
        radioButtonMarried = view.findViewById(R.id.radioButtonMarried);
        radioButtonSingle = view.findViewById(R.id.radioButtonSingle);
        yes_radiobtn=view.findViewById(R.id.yesRadioButton);
        no_radiobtn=view.findViewById(R.id.noRadioButton);
        professionSpinner = view.findViewById(R.id.professionSpinner);
        bloodGroupSpinner = view.findViewById(R.id.bloodGroupSpinner);

        family_details_layout=view.findViewById(R.id.family_details_layout);
        // Create and set adapter
        bloodGroupSpinner.setAdapter(new SpinnerBloodGroupAdapter(context));
        districtSpinner = view.findViewById(R.id.districtSpinner);
        retrofitApiInterface = RetrofitApiClient.getApiClient(context).create(RetrofitApiInterface.class);
        sessionManager = new SessionManager(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

    }

    private void getMemberInfoById() {
        retrofitApiInterface.getMemberInfoById(sessionManager.getRegId()).enqueue(new Callback<PojoMemberInfo>() {
            @Override
            public void onResponse(Call<PojoMemberInfo> call, Response<PojoMemberInfo> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
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
    }

    private void setUserInfo(PojoMemberInfo dto) {

        //*** Blood Group

        String selectedBg = dto.getBlood_group();
        for (int i = 0; i < bloodGroupSpinner.getCount(); i++) {
            String item = bloodGroupSpinner.getItemAtPosition(i).toString();
            if (item.equals(selectedBg)) {
                // If the selected value matches an item in the spinner, set the selection to that index
                bloodGroupSpinner.setSelection(i);
                break; // Exit the loop once the selection is set
            }
        }


        //**** Gender
        String gender = dto.getGender(); // Assuming list is your PojoUserInfo object

        if (gender != null) {
            if (gender.equalsIgnoreCase("Male")) {
                radioButtonMale.setChecked(true);
            } else{
                radioButtonFemale.setChecked(true);
            }
        } else {
            // Handle the case where gender is null or not set
        }


        //**** Married Status
        String marriedStatus = dto.getMarried_status();
        if (marriedStatus != null) {
            if (gender.equalsIgnoreCase("Single")) {
                radioButtonSingle.setChecked(true);
            } {
                radioButtonMarried.setChecked(true);
            }
        } else {
            // Handle the case where gender is null or not set
        }



        int selectedPro = -1;
        for (int i = 0; i < professions.size(); i++) {
            if (professions.get(i).getId() == dto.getProfession_id_fk()) {
                selectedPro = i;
                break;
            }
        }

        if (selectedPro != -1) {
            professionSpinner.setSelection(selectedPro);
        } else {
            // Handle the case where the desired category is not found in the list
        }

        int selectedDist = -1;
        for (int i = 0; i < districts.size(); i++) {
            if (districts.get(i).getId() == dto.getDistrict_id_fk()) {
                selectedDist = i;
                break;
            }
        }

        if (selectedDist != -1) {
            districtSpinner.setSelection(selectedDist);
        } else {
            // Handle the case where the desired category is not found in the list
        }

        nameText.setText(dto.getnAME());
        fatherNameText.setText(dto.getFather());
        motherNameText.setText(dto.getMother());
        occupationText.setText(dto.getOccupation());
        editTextDOB.setText(dto.getDob());
       // mobileNoText.setText(dto.getMobile_no());
        // passText.setText(dto.getpASSWORD());
        whatsappText.setText(dto.getWhatsapp_no());
        emailAddText.setText(dto.getEmail());
        wifeNameText.setText(dto.getWife_name());
        fatherInLawText.setText(dto.getFather_in_law());
        motherInLawText.setText(dto.getMother_in_law());
        childrenText.setText(String.valueOf(dto.getNo_of_children()));
        editTextDOA.setText(dto.getDoa());
        fbText.setText(dto.getFacebook_link());
        instaText.setText(dto.getInstagram_link());
        ytText.setText(dto.getYoutube_link());
        addressText.setText(dto.getAddress());

        liveLocationText.setText(dto.getLive_location());
        childrenNameText.setText(dto.getName_of_children());


        String imageUrl1 = dto.getImage();
        Glide.with(context)
                .load(imageUrl1)
                //.placeholder(R.drawable.about_us)
                .error(R.drawable.img_no_image).into(previewImage1);
        Glide.with(context)
                .load(imageUrl1)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true) // Disable memory caching
                .into(previewImage1);

        String imageUrl2 = dto.getImage1();
        Glide.with(context)
                .load(imageUrl2)
                //.placeholder(R.drawable.about_us)
                .error(R.drawable.img_no_image).into(previewImage2);
        Glide.with(context)
                .load(imageUrl2)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .skipMemoryCache(true) // Disable memory caching
                .into(previewImage2);
    }


    private void showDatePickerDialog() {
        // Get the current date if not already set
        if (selectedDate == null) {
            selectedDate = Calendar.getInstance();
        }

        // Create a DatePickerDialog and set the current date as the default date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        // Update the selectedDate variable
                        selectedDate.set(selectedYear, selectedMonth, selectedDay);

                        // Update the EditText with the selected date
                        String selectedDateStr = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        editTextDOB.setText(selectedDateStr);
                    }
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
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
        //String mobileNo = mobileNoText.getText().toString();
        //String password = passText.getText().toString();
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

        String liveLocation = liveLocationText.getText().toString();
        String nameOfChildren = childrenNameText.getText().toString();

        String image1,image2;
        if(bitmapImage1==null){
            image1 = "";
        }
        else{
            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
            bitmapImage1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1);
            image1 = android.util.Base64.encodeToString(byteArrayOutputStream1.toByteArray(), Base64.DEFAULT);
           // Toast.makeText(context, "" + image1, Toast.LENGTH_SHORT).show();
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


        retrofitApiInterface.updateMemberInfo(sessionManager.getRegId(),
                gender, marriedStatus, hideContact, professionIdFk, districtIdFk, bloodGroup, name, father, mother,
                occupation, dob,  whatsappNo, email, wifeName, fatherInLaw, motherInLaw,
                numberOfChildren, doa, facebookLink, instagramLink, youtubeLink, address,
                liveLocation, nameOfChildren, image1, image2).enqueue(new Callback<PojoDefault>() {
            @Override
            public void onResponse(retrofit2.Call<PojoDefault> call, Response<PojoDefault> response) {
                PojoDefault dto = response.body();
                //Toast.makeText(context, ""+response.body(), Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    if(dto.isStatus()){
                        //Toast.makeText(context, dto.getMessage(), Toast.LENGTH_SHORT).show();

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


    }
    private void showDatePickerDialogForDateOfAnniversity() {

        if (selectedDate == null) {
            selectedDate = Calendar.getInstance();
        }
        // Create a DatePickerDialog and set the current date as the default date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        // Update the EditText with the selected date
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        editTextDOA.setText(selectedDate);
                    }
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
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
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Create the camera_intent ACTION_IMAGE_CAPTURE it will open the camera for capture the image
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Start the activity with camera_intent, and request pic id
            startActivityForResult(camera_intent, galleryPickerId);
        } else {
            // Dialog box to request permissions if not granted
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CAMERA}, galleryPickerId);
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
                        bitmapImage1 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
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
                        bitmapImage2 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
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

    private void getAllDistrict() {
        retrofitApiInterface.getAllDistrict().enqueue(new Callback<List<PojoDistrict>>() {
            @Override
            public void onResponse(Call<List<PojoDistrict>> call, Response<List<PojoDistrict>> response) {
                if (response.isSuccessful()) {
                     districts = response.body();



                    // Create a custom adapter using the list of PojoCategory
                    SpinnerDistrictAdapter adapter = new SpinnerDistrictAdapter(context, districts);

                    // Set the adapter to the Spinner
                    districtSpinner.setAdapter(adapter);

                    getMemberInfoById();


                }
            }

            @Override
            public void onFailure(Call<List<PojoDistrict>> call, Throwable t) {
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
                    getAllDistrict();




                }
            }

            @Override
            public void onFailure(Call<List<PojoProfession>> call, Throwable t) {

            }
        });
    }

}