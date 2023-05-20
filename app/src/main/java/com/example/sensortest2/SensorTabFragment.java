package com.example.sensortest2;

import androidx.fragment.app.Fragment;

public abstract class SensorTabFragment extends Fragment {
    public abstract void setData(int[] sensorData);

    public String addDegreeSymbolToString(String string) {
        return string.concat(getResources().getString(R.string.degree_symbol));
    }
}
