package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends AppCompatActivity {

    AppCompatButton login_btn_id;
    EditText pass_text_id, mobile_no_text_id;
    SessionManager sessionManager = null;
    private AdapterForAddSlider adapter;
    TextView register_link_id;
    ImageButton SignUPtext;
    ViewPager addViewPager;
    RetrofitApiInterface retrofitApiInterface;
    private Runnable runnable;
    private Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //changing color status bar
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_clr));

        pass_text_id = findViewById(R.id.passText);
        mobile_no_text_id = findViewById(R.id.mobileNoText);
        login_btn_id = findViewById(R.id.login_btn_id);
        addViewPager =findViewById(R.id.loginViewPager);
        sessionManager = new SessionManager(ActivityLogin.this);
        register_link_id = findViewById(R.id.register_link_id);
        SignUPtext = findViewById(R.id.SignUPtext);

        ValidateMobileNo();

        retrofitApiInterface = RetrofitApiClient.getApiClient(getApplicationContext()).create(RetrofitApiInterface.class);

        // Set OnClickListener for the login on Button
        login_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        handleLoginAddSlider();

        SignUPtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegistration.class);
                startActivity(intent);
                finish();
            }
        });
        register_link_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegistration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ValidateMobileNo() {



        mobile_no_text_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text change
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action needed during text change
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 10) {
                    s.delete(10, s.length());
                    Toast.makeText(ActivityLogin.this, "Mobile number must be exactly 10 digits", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleLoginAddSlider() {


            retrofitApiInterface.getAllLoginSliderAdd().enqueue(new Callback<List<PojoAdd>>() {
                @Override
                public void onResponse(Call<List<PojoAdd>> call, Response<List<PojoAdd>> response) {
                    if (response.isSuccessful()) {
                        List<PojoAdd> list = response.body();
                        // Initialize the adapter
                        AdapterForAddSlider adapter = new AdapterForAddSlider(getApplicationContext(), list);
                        // Set the adapter to the ViewPager2
                        addViewPager.setAdapter(adapter);
                        runnable = new Runnable() {
                            int currentPage = 0;

                            @Override
                            public void run() {
                                if (currentPage == list.size()) {
                                    currentPage = 0;
                                }
                                addViewPager.setCurrentItem(currentPage++, true);
                                handler.postDelayed(this, 5000); // Slide every 3 seconds
                            }
                        };
                        handler.postDelayed(runnable, 5000);

                    }
                }

                @Override
                public void onFailure(Call<List<PojoAdd>> call, Throwable t) {
                    // Handle failure here if needed
                }
            });




    }

    public void checkLogin() {

        String mobile_value = mobile_no_text_id.getText().toString();
        String pass_value = pass_text_id.getText().toString();


        retrofitApiInterface.checkLogin(mobile_value, pass_value).enqueue(new Callback<PojoLogin>() {


            @Override
            public void onResponse(Call<PojoLogin> call, Response<PojoLogin> response) {
                if (response.isSuccessful()) {
                    PojoLogin list = response.body();
                    if (list.getId() > 0) {
                        //Log.d("API_Response", "Response message: " + list.getMessage());
                        //Log.d("API_Response", "User ID: " + list.getId());
                        //Log.d("API_Response", "Name: " + list.getStudent_name());
                        //Log.d("API_Response", "Status: " + list.isStatus());

                        sessionManager.createSession(list);


                        Intent intent = new Intent(ActivityLogin.this, DashBoardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        // Optional: If you want to finish the current activity
                        finish();

                    } else {
                        Log.d("API_Response", "Response message: " + list.getMessage());
                        //Toast.makeText(getApplicationContext(), list.getMessage(), Toast.LENGTH_SHORT).show();
                        AlertDialog.showIncorrectPasswordDialog(ActivityLogin.this, "Incorrect Mobile || Password", "Please Enter Right mobile no and password . Please try again.");


                    }
                }

            }

            @Override
            public void onFailure(Call<PojoLogin> call, Throwable t) {
                Log.e("API_FAILURE", "Error occurred: " + t.getMessage());

                //Toast.makeText(getApplicationContext(), "Error occurred: ", Toast.LENGTH_SHORT).show();
                AlertDialog.showIncorrectPasswordDialog(ActivityLogin.this, "Server Error", "Server Error Occurred. Please try again.");

            }
        });
    }

}







