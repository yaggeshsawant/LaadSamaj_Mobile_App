package com.madsssoft.laadsamaj;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpinnerProfessionAdapter extends ArrayAdapter<PojoProfession> {
    public SpinnerProfessionAdapter(Context context, List<PojoProfession> entries) {
        super(context, android.R.layout.simple_spinner_item, entries != null ? entries : new ArrayList<>());
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(getItem(position).getnAME());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setText(getItem(position).getnAME());
        return textView;
    }
}

