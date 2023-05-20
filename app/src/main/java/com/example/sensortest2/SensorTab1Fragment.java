package com.example.sensortest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SensorTab1Fragment extends SensorTabFragment {
    private TextView textView;

    public SensorTab1Fragment() {
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
        textView = view.findViewById(R.id.tab1_textView);
        return view;
    }

    @Override
    public void setData(int[] sensorData) {

    }
}