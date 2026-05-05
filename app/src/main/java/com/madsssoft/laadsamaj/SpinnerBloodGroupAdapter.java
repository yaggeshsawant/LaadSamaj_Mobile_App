package com.madsssoft.laadsamaj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerBloodGroupAdapter extends ArrayAdapter<String> {

    private final String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};


    private final LayoutInflater mInflater;
    private final Context mContext;
    private final String[] mValues;

    // Constructor to initialize the adapter with the fixed data
    public SpinnerBloodGroupAdapter(Context context) {
        super(context, android.R.layout.simple_spinner_item);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mValues = new String[]{"A-Positive", "A-Negative", "B-Positive", "B-Negative", "AB-Positive", "AB-Negative", "O-Positive", "O-Negative"};
    }

    @Override
    public int getCount() {
        return mValues.length;
    }

    @Override
    public String getItem(int position) {
        return mValues[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        TextView text = view.findViewById(android.R.id.text1);
        text.setText(mValues[position]);
        return view;
    }
}

