package com.example.sensortest2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Tab3Fragment extends Fragment {

    public Tab3Fragment() {
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
        Button buttonVibrate = view.findViewById(R.id.button_vibrate);
        TextView textViewProximityValue = view.findViewById(R.id.tab3_textview_proximity_value);
        TextView textViewBrightnessValue = view.findViewById(R.id.tab3_textview_brightness_value);
        buttonVibrate.setOnClickListener(v -> MainActivity.vibrate());

        ((MainActivity) requireActivity()).setFragmentRefreshListener(() -> {
            if (MainActivity.proximitySensorValue == 0.0) {
                textViewProximityValue.setText(R.string.yes);
            } else {
                textViewProximityValue.setText(R.string.no);
            }
            textViewBrightnessValue.setText(String.valueOf(MainActivity.lightSensorValue));
        });

        return view;
    }
}