package com.example.sensortest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Tab3 extends SensorTabFragment {
    private static final int PROXIMITY_VALUE_INDEX = 3;
    private static final int BRIGHTNESS_VALUE_INDEX = 4;
    Button buttonVibrate;
    private static final int SHORT_VIBRATION = 40;
    private static final int LONG_VIBRATION = 120;
    private TextView textViewProximityValue;
    private TextView textViewBrightnessValue;
    private int previousProximityValue = -1;

    public Tab3() {
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
        buttonVibrate.setOnClickListener(v -> vibrate(LONG_VIBRATION));
        return view;
    }

    @Override
    public void setData(int[] sensorData) {
        int proximityValue = sensorData[PROXIMITY_VALUE_INDEX];
        int brightnessValue = sensorData[BRIGHTNESS_VALUE_INDEX];

        //avoid vibration on create
        if (previousProximityValue == -1) {
            previousProximityValue = proximityValue;
        }

        if (proximityValue != previousProximityValue) {
            vibrate(SHORT_VIBRATION);
        }
        if (proximityValue == 0) {
            textViewProximityValue.setText(getResources().getString(R.string.yes));
        } else {
            textViewProximityValue.setText(getResources().getString(R.string.no));
        }
        previousProximityValue = proximityValue;

        textViewBrightnessValue.setText(String.valueOf(brightnessValue));
    }

    private void vibrate(int duration) {
        ((MainActivity) requireActivity()).vibrate(duration);
    }
}