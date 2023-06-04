package com.example.sensortest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Tab1 extends SensorTabFragment {
    private static final int ORIENTATION_VALUE_INDEX = 0;
    private ImageView imageView;
    private TextView textView;

    public Tab1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        textView = view.findViewById(R.id.tab1_textview_orientation);
        imageView = view.findViewById(R.id.tab1_imageview_compass);
        return view;
    }

    @Override
    public void setData(int[] sensorData) {
        imageView.setRotation(sensorData[ORIENTATION_VALUE_INDEX]);
        textView.setText(super.addDegreeSymbolToString(String.valueOf(sensorData[ORIENTATION_VALUE_INDEX])));
    }
}