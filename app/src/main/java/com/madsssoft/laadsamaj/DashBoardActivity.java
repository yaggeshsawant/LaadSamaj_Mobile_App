package com.madsssoft.laadsamaj;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity {
    // Define a constant for the permission request

    Context context;
    DrawerLayout drawer_layout_id;
    Toolbar toolbar_id;
    ActionBarDrawerToggle toggle;
    FrameLayout frame_drawer_id;
    BottomNavigationView bottom_navbar_id;
    NavigationView navigation_view_id;

    ImageView callIcon, whatsappIcon, emailIcon, whatsappheadIcon, sidebarImageView, imageWebView, fb_img_id, imageSearchId;
    TextView sidebarTextView, logout_id, editProfile;
    ProgressDialog progressDialog;
    Dialog customDialog;
    SessionManager sessionManager = null;

    RetrofitApiInterface retrofitApiInterface;
    private static final int CALL_PHONE_PERMISSION_REQUEST = 123; // You can use any integer value


    private static final int DELAY_MILLIS = 5000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board_activity);

        initialization();
        //for changing color status bar
        getWindow().setStatusBarColor(getResources().getColor(R.color.primary_clr));
        checkLogin();
        // Show the custom design as a dialog
        handleCustomDialogForAd();
        // handle sidebar and header icon click
        handleIconClick();
        // navigationItem selected listener on NavigationView
        handleNavigationViewListener();
        handleNavIconOnClick();
        // handle bottom tab selected listener
        handleBottomTabSelectListener(); // user defined method
        //checkAndRequestPhoneStatePermission();
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
        }*/


    }

    public void initialization() {
        context = this;
        toolbar_id = findViewById(R.id.toolbar_id);
        drawer_layout_id = findViewById(R.id.drawer_layout_id);
        frame_drawer_id = findViewById(R.id.frame_drawer_id);
        bottom_navbar_id = findViewById(R.id.bottom_navbar_id);
        navigation_view_id = findViewById(R.id.navigation_menu_id);

        callIcon = findViewById(R.id.call_img_id);
        whatsappIcon = findViewById(R.id.whatsapp_img_id);
        whatsappheadIcon = findViewById(R.id.whatsapp_head_img_id);
        emailIcon = findViewById(R.id.email_img_id);
        fb_img_id = findViewById(R.id.fb_img_id);
        sidebarTextView = findViewById(R.id.sidebarTextView);
        logout_id = findViewById(R.id.logout_id);
        editProfile = findViewById(R.id.editProfile);
        sidebarImageView = findViewById(R.id.sidebarImageView);
        imageWebView = findViewById(R.id.imageWebView);
        imageSearchId = findViewById(R.id.imageSearchId);
        sessionManager = new SessionManager(DashBoardActivity.this);

        retrofitApiInterface = RetrofitApiClient.getApiClient(this).create(RetrofitApiInterface.class);


        // default fragment layout show on load
        getSupportFragmentManager().beginTransaction().replace(frame_drawer_id.getId(), new FragmentHome()).commit();
        setSupportActionBar(toolbar_id);

        // below line for creation of toggle
        toggle = new ActionBarDrawerToggle(this, drawer_layout_id, toolbar_id, R.string.Open, R.string.Close);
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, android.R.color.black));

        //  add created toggle
        drawer_layout_id.addDrawerListener(toggle);
        toggle.syncState();

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

    }

    private void handleCustomDialogForAd() {
        //progressDialog.show();
        retrofitApiInterface.getPopUpAddInfo().enqueue(new Callback<PojoAdd>() {
            @Override
            public void onResponse(Call<PojoAdd> call, Response<PojoAdd> response) {


                if (response.isSuccessful() && response.body() != null) {
                    //Log.d("API_Response", "Response Message: " + response.body());
                    //Toast.makeText( getApplicationContext(), "AD FETCHED", Toast.LENGTH_SHORT).show()
                    PojoAdd list = response.body();
                    if (list.getA_id() > 0) {
                        // Calling Add Dialog
                        String adImageUrl = list.getImage_formate();
                        //Toast.makeText( getApplicationContext(), "Image"+adImageUrl, Toast.LENGTH_SHORT).show();
                        //Toast.makeText( getApplicationContext(), "Ad"+list.getLink(), Toast.LENGTH_SHORT).show();
                        if (ImageAvailabilityChecker.isImageAvailable(getApplicationContext(), adImageUrl)) {
                            CustomDialogAd.showDialog(context, adImageUrl, list.getLink());
                        }

                    }

                }
                //progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<PojoAdd> call, Throwable t) {
                Log.d("API_Response", "Response message: " + "ERROR OCCURRED");
                Toast.makeText(getApplicationContext(), "AD ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }
        });
    }

    private void handleIconClick() {
        imageWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://laadsamaj.com/"; // Replace with the URL of the website you want to open
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        callIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action for calling
                // For example:
                // Start a phone call intent
                /*Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9826607332"));
                startActivity(intent);*/

                // Check if the app has the CALL_PHONE permission
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // If the permission is granted, initiate the phone call
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:9826607332"));
                    startActivity(callIntent);
                } else {
                    // If the permission is not granted, request it from the user
                    ActivityCompat.requestPermissions(DashBoardActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST);
                }
            }
        });
        fb_img_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String facebookPageUrl = "https://www.facebook.com/MyKhandwa"; // Replace with your Facebook Page URL
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookPageUrl)));
            }
        });

        whatsappIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWhatsappChat();
            }
        });


        whatsappheadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWhatsappChat();
            }
        });


        emailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:madss@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(intent);
            }
        });

        logout_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutSession();
            }
        });


    }

    private void handleNavIconOnClick() {


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer_layout_id.close();

                Fragment selectedFragment = new FragmentEditProfile();

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_drawer_id);

                if (currentFragment.getClass() != selectedFragment.getClass()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_drawer_id, selectedFragment).addToBackStack(null).commit();


                }



                /*Fragment selectedFragment = new FragmentEditRegistration();

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_drawer_id);

                if (currentFragment.getClass() != selectedFragment.getClass()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_drawer_id, selectedFragment).addToBackStack(null).commit();


                }*/
            }
        });
        imageSearchId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment selectedFragment = new FragmentAllMembersInfoBySearch();

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_drawer_id);


                if (currentFragment.getClass() != selectedFragment.getClass()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_drawer_id, selectedFragment).addToBackStack(null).commit();


                }
            }
        });
    }
/*    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.top_navigation_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle menu item clicks inside the PopupMenu
                // Handle settings action
                // Add more cases as needed
                return item.getItemId() == R.id.top_home_id;
            }
        });
        popupMenu.show();
    }*/

    public void goToWhatsappChat() {
        String phoneNumber = "9826607332"; // Replace this with the desired phone number
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


    public void handleNavigationViewListener() {
        navigation_view_id.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                Bundle bundle = new Bundle();

                if (item.getItemId() == R.id.nav_home_id) {
                    selectedFragment = new FragmentHome();
                } else if (item.getItemId() == R.id.nav_about_id) {
                    selectedFragment = new FragmentAboutUs();
                } else if (item.getItemId() == R.id.nav_gallery_id) {
                    bundle.putString("type", "gallery");
                    selectedFragment = new FragmentAllSamaj();
                    selectedFragment.setArguments(bundle);
                } else if (item.getItemId() == R.id.nav_event_id) {
                    bundle.putString("type", "gallery");
                    selectedFragment = new FragmentEvents();
                    selectedFragment.setArguments(bundle);
                } else if (item.getItemId() == R.id.nav_client_id) {
                    bundle.putString("type", "client");
                    selectedFragment = new FragmentAllSamaj();
                    selectedFragment.setArguments(bundle);
                } else if (item.getItemId() == R.id.nav_media_id) {
                    bundle.putString("type", "paperCutting");
                    selectedFragment = new FragmentAllSamaj();
                    selectedFragment.setArguments(bundle);
                }  else if (item.getItemId() == R.id.nav_contact_id) {
                    selectedFragment = new FragmentContactUs();
                } else if (item.getItemId() == R.id.nav_feedback_id) {
                    selectedFragment = new FragmentFeedback();
                } else if (item.getItemId() == R.id.nav_mem_bus_id) {
                    selectedFragment = new FragmentAllRegistrationOfMember();
                } else if (item.getItemId() == R.id.nav_mem_relation_req_id) {
                    selectedFragment = new FragmentRequestRelation();
                } else if (item.getItemId() == R.id.nav_mem_relation_id) {
                    selectedFragment = new FragmentMemberRelation();
                } else if (item.getItemId() == R.id.nav_change_pass_id) {
                    selectedFragment = new FragmentChangePassword();
                } else if (item.getItemId() == R.id.nav_logout_id) {
                    sessionManager.logoutSession();
                }


                drawer_layout_id.close();
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(frame_drawer_id.getId(), selectedFragment).addToBackStack(null).commit();
                }
                return true;
            }
        });

    }

    private void handleBottomTabSelectListener() {




        bottom_navbar_id.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id= item.getItemId();

                if(id==R.id.bottom_home_id){
                    selectedFragment= new FragmentHome();
                } else if (id==R.id.bottom_directory_id) {
                    selectedFragment = new FragmentMemberByCitiesOrProfession();
                }
                else if (id==R.id.bottom_business_directory_id) {
                    selectedFragment = new FragmentAllBusinessCategory();
                }
                else{
                    selectedFragment= new FragmentMatrimonial();
                }


                return loadFragment(selectedFragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_drawer_id, fragment).commit();
            return true;
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        /* if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Toast.makeText(DashBoardActivity.this, "SERIAL NO : "+Build.SERIAL, Toast.LENGTH_SHORT).show();
        Log.d("Tag",Build.SERIAL);*/
        // Check for internet connectivity in a background thread

        new CheckInternetConnectivityTask().execute();
    }

    public class CheckInternetConnectivityTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return isNetworkConnected();
        }

        @Override
        protected void onPostExecute(Boolean isNetworkConnected) {
            if (!isNetworkConnected) {
                showNoInternetActivity();
            }
        }
    }

    private void showNoInternetActivity() {
        Toast.makeText(this, "NO INTERNET", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, NoInternetActivity.class);
        startActivity(i);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (requestCode == CALL_PHONE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initiate the phone call
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9826607332"));
                startActivity(callIntent);
            } else {
                // Permission denied
                Toast.makeText(this, "Call permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkLogin() {
        if (sessionManager.getRegId() != 0) {
            //Toast.makeText(context, ""+sessionManager.getRegId(), Toast.LENGTH_SHORT).show();
            retrofitApiInterface.getMemberInfoById(sessionManager.getRegId()).enqueue(new Callback<PojoMemberInfo>() {
                @Override
                public void onResponse(Call<PojoMemberInfo> call, Response<PojoMemberInfo> response) {
                    if (response.isSuccessful()) {
                        //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        PojoMemberInfo list = response.body();
                        if (list.getId() != 0) {

                            Glide.with(context)
                                    .load(list.getImage())
                                    .placeholder(R.drawable.img_no_image)
                                    .error(R.drawable.img_no_image).diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                                    .skipMemoryCache(true).into(sidebarImageView);


                            sidebarTextView.setText(list.getnAME());
                        } else {
                            changeIntent(ActivityLogin.class, null);
                        }
                    } else {
                        changeIntent(ActivityLogin.class, null);
                    }

                }

                @Override
                public void onFailure(Call<PojoMemberInfo> call, Throwable t) {
                    Toast.makeText(context, "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                    changeIntent(ActivityLogin.class, null);
                }
            });

        } else {
            changeIntent(ActivityLogin.class, null);
        }
    }

    public void changeIntent(Class<? extends Activity> activityClass, Bundle bundle) {
        Intent intent = new Intent(DashBoardActivity.this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        startActivity(intent);
        finish();
    }


}