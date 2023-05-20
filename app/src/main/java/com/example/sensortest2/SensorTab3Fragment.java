package com.example.sensortest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SensorTab3Fragment extends SensorTabFragment {
    Button buttonVibrate;
    TextView textViewProximityValue;
    TextView textViewBrightnessValue;

    public SensorTab3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        buttonVibrate = view.findViewById(R.id.button_vibrate);
        textViewProximityValue = view.findViewById(R.id.tab3_textview_proximity_value);
        textViewBrightnessValue = view.findViewById(R.id.tab3_textview_brightness_value);
        buttonVibrate.setOnClickListener(v -> ((MainActivity) getActivity()).vibrate());
        return view;
    }

    @Override
    public void setData(int[] sensorData) {
        int proximityValue = sensorData[2];
        int brightnessValue = sensorData[3];

        if (proximityValue == 0) {
            textViewProximityValue.setText(getResources().getString(R.string.yes));
        } else {
            textViewProximityValue.setText(getResources().getString(R.string.no));
        }
        textViewBrightnessValue.setText(String.valueOf(brightnessValue));
    }
}