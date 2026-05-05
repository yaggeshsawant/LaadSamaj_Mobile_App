package com.madsssoft.laadsamaj;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMemberByCitiesOrProfession extends Fragment {

    ProgressDialog progressDialog;
    Button cityButton, professionButton;

    ListView listView;
    List<PojoDistrict> districts = null; // Ensure that this is initialized properly when data is fetched

    RetrofitApiInterface retrofitApiInterface;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_member_by_cities_or_profession, container, false);
        initialization(view);


        // Reference to the ListView
        listView= view.findViewById(R.id.city_list);



        // Fetch districts and set up the listview
        getAllDistrict();



        CitiesAction();
        ProfessionAction();

        return view;
    }
    private void initialization(View view) {

        cityButton = view.findViewById(R.id.cityButton);
        professionButton = view.findViewById(R.id.professionButton);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Please Wait...");

        retrofitApiInterface = RetrofitApiClient.getApiClient(getActivity()).create(RetrofitApiInterface.class);


    }


    private void CitiesAction() {
        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the current fragment with the new one
                Fragment fragment= new FragmentMembersByCities();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_drawer_id, fragment)  // R.id.frame_id is the container for your fragments
                        .addToBackStack(null)  // Add the transaction to the back stack
                        .commit();
            }
        });
    }

    private void ProfessionAction() {
        professionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the current fragment with the new one
                Fragment fragment= new FragmentAllProfession();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_drawer_id, fragment)  // R.id.frame_id is the container for your fragments
                        .addToBackStack(null)  // Add the transaction to the back stack
                        .commit();
            }
        });
    }

    // Method to fetch all districts and populate the Spinner
    private void getAllDistrict() {
        progressDialog.show();
        retrofitApiInterface.getAllDistrict().enqueue(new Callback<List<PojoDistrict>>() {
            @Override
            public void onResponse(Call<List<PojoDistrict>> call, Response<List<PojoDistrict>> response) {
                if (response.isSuccessful()) {
                    districts = response.body(); // Populate districts list
                    List<String> cities= new ArrayList<>();
                    for(int i=0;i<districts.size();i++){
                        cities.add(districts.get(i).getnAME());
                    }

                    if (districts != null && !districts.isEmpty()) {
                        // Creating an ArrayAdapter to bind data to ListView
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, cities);

                        // Setting the adapter on the ListView
                        listView.setAdapter(adapter);
                        // Set onClickListener for list items
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                // Handle the click event
                                int cityId = districts.get(position).getId(); // Get the clicked item


                                // Create an instance of the destination fragment
                                FragmentMembersViewByCities fragment = new FragmentMembersViewByCities();

                                // Create a Bundle to pass the data
                                Bundle bundle = new Bundle();
                                bundle.putInt("cityId", cityId);

                                // Set the bundle as the arguments for Fragment B
                                fragment.setArguments(bundle);

                                // Use FragmentTransaction to replace the current fragment with new fragment
                                FragmentManager fragmentManager = getParentFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_drawer_id, fragment);
                                fragmentTransaction.addToBackStack(null);  // Optionally add to back stack
                                fragmentTransaction.commit();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "No districts found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("API_Response", "Failed to load districts");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PojoDistrict>> call, Throwable t) {
                Log.d("API_Response", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Failed to load districts", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}