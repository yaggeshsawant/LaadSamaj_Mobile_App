package com.madsssoft.laadsamaj;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import io.github.chayanforyou.slider.SliderLayout;
import io.github.chayanforyou.slider.animations.DescriptionAnimation;
import io.github.chayanforyou.slider.slidertypes.BaseSliderView;
import io.github.chayanforyou.slider.slidertypes.TextSliderView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {
    Context context = null;
    View view;
    SliderLayout mDemoSlider;
    ViewPager frontAddView;
    ProgressDialog progressDialog;
    RecyclerView servicesRecycleId, onlineDirectoryRecycleId, countsRecyclerId, allSamajReclycleId, latestRegRecycleId;
    private TextView newsTextView;
    ImageView searchRegistrationButton;
    Button registerBusinessButton;
    RetrofitApiInterface retrofitApiInterface = null;
    private Runnable runnable;
    private Handler handler = new Handler();
   PojoMemberCount dto;

    private SwipeRefreshLayout swipeRefreshLayout;

    // Override the onCreateView method to inflate the fragment's layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.view = view;



        // handle all initialization
        initialization();
        // Handle marquee setup
        handleMarquee();
        setOnClickInBtn();
        // Handle Slider setup
        handleSlider();

        handleServicesRecyclerView();
        handleCountsRecyclerView();
        handleOnlineDirectoryRecyclerView();
        //   handlePlacedStudentRecyclerView();
        handleAllSamajRecyclerView();
        handleLatestRegRecycleView();
        handleHomeAddSlider();


        //Initialization of swipe refresh layout
        swipeRefreshLayout=view.findViewById(R.id.swipe_layout_id);

        //handle the refresh action
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Handle marquee setup
                handleMarquee();
                setOnClickInBtn();
                // Handle Slider setup
                handleSlider();

                handleServicesRecyclerView();
                handleCountsRecyclerView();
                handleOnlineDirectoryRecyclerView();
                //   handlePlacedStudentRecyclerView();
                handleAllSamajRecyclerView();
                handleLatestRegRecycleView();
                handleHomeAddSlider();


                //stop the refreshing animation once data is loaded
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // handleStuFeedbackRecycleView();
        // Set back button press listener
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle back press within the fragment
                CustomDialogExit.showDialog(context);
            }
        });


        return view;
    }


    public void initialization() {

        context = getActivity();
        newsTextView = view.findViewById(R.id.news);
        mDemoSlider = view.findViewById(R.id.slider);

        latestRegRecycleId = view.findViewById(R.id.latestRegRecycleId);
        //  placedStudentRecycleId = view.findViewById(R.id.placedStudentRecycleId);
        allSamajReclycleId = view.findViewById(R.id.allSamajReclycleId);

        frontAddView = view.findViewById(R.id.addViewPager);
        servicesRecycleId = view.findViewById(R.id.servicesRecycleId);
        onlineDirectoryRecycleId = view.findViewById(R.id.onlineDirectoryRecycleId);

        //stuFeedbackRecycleId = view.findViewById(R.id.stuFeedbackRecycleId);
        searchRegistrationButton = view.findViewById(R.id.searchRegistrationButton);
        countsRecyclerId = view.findViewById(R.id.countsRecyclerId);
        registerBusinessButton = view.findViewById(R.id.registerBusinessButton);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");
        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);

    }

    ;

    private void handleMarquee() {

        retrofitApiInterface.getAllNewsInfo().enqueue(new Callback<List<PojoNews>>() {
            @Override
            public void onResponse(Call<List<PojoNews>> call, Response<List<PojoNews>> response) {
                if (response.isSuccessful()) {
                    List<PojoNews> newList = response.body();
                    Log.d("API_Response", "Response Message: " + response.body());
                    if (newList != null && newList.size() > 0) {
                        String finalMessage = "";
                        for (int i = 0; i < newList.size(); i++) {
                            PojoNews list = newList.get(i);

                            finalMessage += " ||   * " + list.getName() + ".";

                            // Set click listener for the TextView
                            /*newsTextView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Open link for the current message
                                    openLink();
                                }
                            });*/
                        }

                        // Set initial text
                        newsTextView.setText(finalMessage);
                        newsTextView.setTypeface(null);
                        newsTextView.setSelected(true);
                    }

                } else {


                }
            }

            @Override
            public void onFailure(Call<List<PojoNews>> call, Throwable t) {
                // Set initial text
                newsTextView.setText("This is Default Message");
                newsTextView.setTypeface(null);
                newsTextView.setSelected(true);

                Log.d("API_Response", "Response message: " + "ERROR OCCURRED");
                Toast.makeText(getActivity(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setOnClickInBtn() {
        registerBusinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_drawer_id, new FragmentRegistration()).addToBackStack(null).commit();
            }
        });
        registerBusinessButton.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.register_btn_anim)); // Assuming animation is saved as pulse.xml


        searchRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_drawer_id, new FragmentAllRegistrationInfoBySearch()).addToBackStack(null).commit();
            }
        });
        /*searchRegistrationButton.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.register_btn_anim)); // Assuming animation is saved as pulse.xml
         */
    }

    private void handleSlider() {
        progressDialog.show();
        retrofitApiInterface.getAllSliderInfo().enqueue(new Callback<List<PojoSlider>>() {
            @Override
            public void onResponse(Call<List<PojoSlider>> call, Response<List<PojoSlider>> response) {
                if (response.isSuccessful()) {
                    List<PojoSlider> newList = response.body();
                    Log.d("API_Response", "Response Message: " + response.body());

                    if (newList != null && newList.size() > 0) {
                        for (int i = 0; i < newList.size(); i++) {
                            PojoSlider list = newList.get(i);

                            String imageUrl = list.getImage_formate();
                            TextSliderView sliderView = new TextSliderView(getActivity());

                            // Initialize a SliderLayout
                            sliderView/*.description(list.getName())*/
                                    .image(imageUrl)
                                    .setScaleType(BaseSliderView.ScaleType.Fit);

                            // Add SliderView to the SliderLayout
                            mDemoSlider.addSlider(sliderView);
                        }
                    }
                } else {
                    setDefaultSlider();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoSlider>> call, Throwable t) {
                // Handle failure scenario if needed
                progressDialog.dismiss();
                setDefaultSlider();
                Log.d("API_Response", "Response message: " + "ERROR OCCURRED");
                Toast.makeText(getActivity(), "ERROR OCCURRED IN FETCHING SLIDER", Toast.LENGTH_SHORT).show();

            }
        });
        mDemoSlider.setDuration(4000);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    }


    // Method to open fragments
    private void openFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_drawer_id, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void setDefaultSlider() {
        // Handle unsuccessful response
        for (int i = 0; i < 4; i++) {
            TextSliderView sliderView = new TextSliderView(getActivity());

            // Initialize a SliderLayout with default image
            sliderView/*.description("Default")*/
                    .image(R.drawable.img_no_image)
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            // Add SliderView to the SliderLayout
            mDemoSlider.addSlider(sliderView);
        }


    }

    public void openWebViewByUrl(String url) {
        // FragmentTransaction to replace the existing fragment with FragmentWebView
        if (getActivity() != null) {
            FragmentWebView fragmentWebView = new FragmentWebView();

            // Bundle to pass data to FragmentWebView
            Bundle args = new Bundle();
            args.putString("url", url);
            fragmentWebView.setArguments(args);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_drawer_id, fragmentWebView)
                    .addToBackStack(null)
                    .commit();
        }
    }


   /* private void handlePlacedStudentRecyclerView() {
        retrofitApiInterface.getAllPlaceStudentsInfo().enqueue(new Callback<List<PojoPlacedStudents>>() {
            @Override
            public void onResponse(Call<List<PojoPlacedStudents>> call, Response<List<PojoPlacedStudents>> response) {
                if (response.isSuccessful()) {
                    List<PojoPlacedStudents> newList = response.body();
                    AdapterForPlacedStudents adaptor = new AdapterForPlacedStudents(newList, getActivity());

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);

                    // Set the layout manager and adapter for the RecyclerView
                    placedStudentRecycleId.setLayoutManager(gridLayoutManager);
                    placedStudentRecycleId.setAdapter(adaptor);

                }
            }

            @Override
            public void onFailure(Call<List<PojoPlacedStudents>> call, Throwable t) {

            }
        });
    }*/


    private void handleAllSamajRecyclerView() {
        retrofitApiInterface.getAllSamajInfo().enqueue(new Callback<List<PojoSamaj>>() {
            @Override
            public void onResponse(Call<List<PojoSamaj>> call, Response<List<PojoSamaj>> response) {
                if (response.isSuccessful()) {
                    List<PojoSamaj> newList = response.body();
                    AdapterForSamaj adaptor = new AdapterForSamaj(newList, getActivity(), new FragmentClient());

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);

                    // Set the layout manager and adapter for the RecyclerView
                    allSamajReclycleId.setLayoutManager(gridLayoutManager);
                    allSamajReclycleId.setAdapter(adaptor);

                }
            }

            @Override
            public void onFailure(Call<List<PojoSamaj>> call, Throwable t) {

            }
        });
    }


    private void handleOnlineDirectoryRecyclerView() {

        retrofitApiInterface.getALlFrontCategoryInfo().enqueue(new Callback<List<PojoCategory>>() {
            @Override
            public void onResponse(Call<List<PojoCategory>> call, Response<List<PojoCategory>> response) {
                if (response.isSuccessful()) {
                    List<PojoCategory> list = response.body();

                    AdapterForFrontBusinessDirectory adaptor = new AdapterForFrontBusinessDirectory(list, getActivity(), R.layout.adapter_item_for_front_business_directory);

                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);

                    // Set the layout manager and adapter for the RecyclerView
                    onlineDirectoryRecycleId.setLayoutManager(gridLayoutManager);
                    onlineDirectoryRecycleId.setAdapter(adaptor);

                   /* Handler handler = new Handler();
                    int delay = 3000; // milliseconds

                    Runnable scrollRunnable = new Runnable() {
                        @Override
                        public void run() {
                            if (gridLayoutManager.findLastCompletelyVisibleItemPosition() < (adaptor.getItemCount() - 1)) {
                                gridLayoutManager.smoothScrollToPosition(onlineDirectoryRecycleId, new RecyclerView.State(), gridLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                            } else {
                                gridLayoutManager.smoothScrollToPosition(onlineDirectoryRecycleId, new RecyclerView.State(), 0);
                            }
                            // Schedule the next scroll after the specified delay
                            handler.postDelayed(this, delay);
                        }
                    };
                    // Initial delayed execution
                    handler.postDelayed(scrollRunnable, delay);
*/
                }
            }

            @Override
            public void onFailure(Call<List<PojoCategory>> call, Throwable t) {

            }
        });


    }


    private void handleLatestRegRecycleView() {


        retrofitApiInterface.getLatestRegistrationsInfo().enqueue(new Callback<List<PojoRegistrationInfo>>() {
            @Override
            public void onResponse(Call<List<PojoRegistrationInfo>> call, Response<List<PojoRegistrationInfo>> response) {
                if (response.isSuccessful()) {
                    List<PojoRegistrationInfo> list = response.body();

                    AdapterForRegistrationInfo adapter = new AdapterForRegistrationInfo(getActivity(), list);
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);
                    latestRegRecycleId.setLayoutManager(layoutManager);
                    latestRegRecycleId.setAdapter(adapter);




                    // Set initial position
                    latestRegRecycleId.scrollToPosition(0);
                    // Automatic sliding
                    runnable = new Runnable() {
                        int currentPage = 0;

                        @Override
                        public void run() {
                            if (currentPage == list.size()) {
                                currentPage = 0;
                            }
                            latestRegRecycleId.smoothScrollToPosition(currentPage++);
                            handler.postDelayed(this, 3000); // Slide every 3 seconds
                        }
                    };
                    handler.postDelayed(runnable, 3000);

                }
            }

            @Override
            public void onFailure(Call<List<PojoRegistrationInfo>> call, Throwable t) {

            }
        });
    }


    private void handleServicesRecyclerView() {
        String[] name = {"सामाजिक", "बिजनेस", "मेट्रोमोनियल", "ब्लड"};
        Fragment[] nextFragment = {new FragmentMemberByCitiesOrProfession(), new FragmentAllBusinessCategory(), new FragmentMatrimonial(), new FragmentAllMemberByBloodGroup()};
        int[] recycler_img = {R.drawable.directory_24, R.drawable.business_24, R.drawable.metrimonial_24, R.drawable.blood_24};

        AdapterForServicesTypes adaptor = new AdapterForServicesTypes(recycler_img, name, nextFragment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);

        servicesRecycleId.setLayoutManager(gridLayoutManager);
        servicesRecycleId.setAdapter(adaptor);



    }

    private void handleCountsRecyclerView() {
progressDialog.show();
        List<PojoCounts> countsList = new ArrayList<>();

        retrofitApiInterface.getMemberCount().enqueue(new Callback<PojoMemberCount>() {
            @Override
            public void onResponse(Call<PojoMemberCount> call, Response<PojoMemberCount> response) {
                if(response.isSuccessful()){
                    dto=response.body();
                    if(dto!=null){

                            countsList.add(new PojoCounts("पुरुष", dto.getMale(), R.drawable.male_24));
                            countsList.add(new PojoCounts("महिला", dto.getFemale(), R.drawable.female_24));
                            countsList.add(new PojoCounts("परिवार", dto.getFamily(), R.drawable.karykarni_24));
                            countsList.add(new PojoCounts("लड़के", dto.getUnmarried_male(), R.drawable.metrimonial_24));
                            countsList.add(new PojoCounts("लड़कियां",dto.getUnmarried_FeMale(), R.drawable.metrimonial_24));


                        AdapterForCounts adaptor = new AdapterForCounts(context, countsList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);
                        // Set the layout manager and adapter for the RecyclerView
                        countsRecyclerId.setLayoutManager(gridLayoutManager);
                        countsRecyclerId.setAdapter(adaptor);
                    }
                    else{
                        Toast.makeText(context, "there is no data in dto", Toast.LENGTH_SHORT).show();
                    }


                }

                else{
                    Toast.makeText(context, "response is not successful", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<PojoMemberCount> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();

            }
        });


//        retrofitApiInterface.getMemberCount().enqueue(new Callback<List<PojoMemberCount>>() {
//            @Override
//            public void onResponse(Call<List<PojoMemberCount>> call, Response<List<PojoMemberCount>> response) {
//                if(response.isSuccessful()){
//                    dto=response.body();
//                    if(dto!=null){
//                        for (PojoMemberCount memberCount : dto) {
//                            countsList.add(new PojoCounts("Male", memberCount.getMale(), R.drawable.myknwlogo, "4521"));
//                            countsList.add(new PojoCounts("Female", memberCount.getFemale(), R.drawable.myknwlogo, "4521"));
//                            countsList.add(new PojoCounts("Family", memberCount.getFamily(), R.drawable.myknwlogo, "4521"));
//                            countsList.add(new PojoCounts("Unmarried male", memberCount.getUnmarried_male(), R.drawable.myknwlogo, "4521"));
//                            countsList.add(new PojoCounts("Unmarried female",memberCount.getUnmarried_FeMale(), R.drawable.myknwlogo, "4521"));
//                        }
//
//
//
//                        AdapterForCounts adaptor = new AdapterForCounts(context, countsList);
//                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);
//                        // Set the layout manager and adapter for the RecyclerView
//                        countsRecyclerId.setLayoutManager(gridLayoutManager);
//                        countsRecyclerId.setAdapter(adaptor);
//                    }
//                    else{
//                        Toast.makeText(context, "there is no data in dto", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                else{
//                    Toast.makeText(context, "response is not successful", Toast.LENGTH_SHORT).show();
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<List<PojoMemberCount>> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
//            }
//        });



    }


    /*private void handleFrontAddView() {
        retrofitApiInterface.getAllFrontAddInfo().enqueue(new Callback<List<PojoAdd>>() {
            @Override
            public void onResponse(Call<List<PojoAdd>> call, Response<List<PojoAdd>> response) {
                if (response.isSuccessful()) {
                    List<PojoAdd> list = response.body();
                    // Assuming you have already set up the adapter (AdapterForFrontImage)
                    AdapterForFrontImage adapter = new AdapterForFrontImage(context, list);
                    // Set the adapter to the ViewPager2
                    frontAddView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PojoAdd>> call, Throwable t) {
                // Handle failure here if needed
            }
        });
    }*/

    private void handleHomeAddSlider() {


        retrofitApiInterface.getAllHomeSliderAdd().enqueue(new Callback<List<PojoAdd>>() {
            @Override
            public void onResponse(Call<List<PojoAdd>> call, Response<List<PojoAdd>> response) {
                if (response.isSuccessful()) {
                    List<PojoAdd> list = response.body();
                    // Initialize the adapter
                    AdapterForAddSlider adapter = new AdapterForAddSlider(context, list);
                    // Set the adapter to the ViewPager2
                    frontAddView.setAdapter(adapter);

                    runnable = new Runnable() {
                        int currentPage = 0;

                        @Override
                        public void run() {
                            if (currentPage == list.size()) {
                                currentPage = 0;
                            }
                            frontAddView.setCurrentItem(currentPage++, true);
                            handler.postDelayed(this, 3000); // Slide every 3 seconds
                        }
                    };
                    handler.postDelayed(runnable, 3000);
                }
            }

            @Override
            public void onFailure(Call<List<PojoAdd>> call, Throwable t) {
                // Handle failure here if needed
            }
        });



    }


}
