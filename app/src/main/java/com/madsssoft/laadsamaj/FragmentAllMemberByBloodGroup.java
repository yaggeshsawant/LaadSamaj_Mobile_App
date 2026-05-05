package com.madsssoft.laadsamaj;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentAllMemberByBloodGroup extends Fragment {
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_member_by_blood, container, false);
        context = view.getContext();

        // Reference to the ListView
        ListView listView = view.findViewById(R.id.BloodGroup_list);

        // Data for the ListView
        String[] data = {"A-Positive", "A-Negative", "B-Positive", "B-Negative", "AB-Positive", "AB-Negative", "O-Positive", "O-Negative"};

        // Creating an ArrayAdapter to bind data to ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);

        // Setting the adapter on the ListView
        listView.setAdapter(adapter);

        // Set onClickListener for list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle the click event
                String selectedItem = data[position]; // Get the clicked item


            // Create an instance of the destination fragment
                FragmentMemberBloodGroupwise fragment = new FragmentMemberBloodGroupwise();

                // Create a Bundle to pass the data
                Bundle bundle = new Bundle();
                bundle.putString("bloodGroup", selectedItem);

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


        return view;
    }


}
