package com.madsssoft.laadsamaj;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerBloodAdapter extends ArrayAdapter<String> {
    private final String[] bloodGroups;

    public SpinnerBloodAdapter(Context context) {
        super(context, android.R.layout.simple_spinner_item);
        this.bloodGroups = new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(getItem(position));
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setText(getItem(position));
        return textView;
    }

    @Override
    public int getCount() {
        return bloodGroups.length;
    }

    @Override
    public String getItem(int position) {
        return bloodGroups[position];
    }
}
