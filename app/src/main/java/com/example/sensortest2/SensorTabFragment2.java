package com.example.sensortest2;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SensorTabFragment2 extends SensorTabFragment {
    private static final int VERTICAL_VALUE_INDEX = 1;
    private static final int HORIZONTAL_VALUE_INDEX = 2;
    private TextView textViewHorizontalValue;
    private TextView textViewVerticalValue;
    private ImageView imageViewHorizontalDot;
    private ImageView imageViewVerticalDot;

    public SensorTabFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        textViewHorizontalValue = view.findViewById(R.id.tab2_textview_horizontal_value);
        textViewVerticalValue = view.findViewById(R.id.tab2_textview_vertical_value);
        imageViewHorizontalDot = view.findViewById(R.id.tab2_imageview_horizontal_dot);
        imageViewVerticalDot = view.findViewById(R.id.tab2_imageview_vertical_dot);
        return view;
    }

    @Override
    public void setData(int[] sensorData) {
        int vertical = sensorData[VERTICAL_VALUE_INDEX];
        int horizontal = sensorData[HORIZONTAL_VALUE_INDEX];

        // reduces the value range from [-180;180] to [-90;90]
        int horizontalValue = reduceValueRange(horizontal, 180, 90);

        setTextViewValue(textViewHorizontalValue, horizontalValue);
        setTextViewValue(textViewVerticalValue, vertical);

        moveDots(vertical, horizontalValue);
    }

    /**
     * dp depends on pixel density of display
     *
     * @param dp input dp
     * @return output px
     */
    private int convertDpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void setTextViewValue(TextView textView, int value) {
        textView.setText(super.addDegreeSymbolToString(String.valueOf(value)));
    }

    private int reduceValueRange(int valueToBeReduced, int oldLimit, int newLimit) {
        int newValue;
        if (valueToBeReduced < -newLimit) {
            newValue = -valueToBeReduced - oldLimit;
        } else if (valueToBeReduced > newLimit) {
            newValue = -valueToBeReduced + oldLimit;
        } else {
            newValue = valueToBeReduced;
        }
        return newValue;
    }

    private void moveDots(int vertical, int horizontal) {
        // calculate values in range [-1,1]
        double doubleVal1 = vertical / 90.0;
        double doubleVal2 = horizontal / 90.0;

        int leftMargin = (int) (-doubleVal2 * convertDpToPx(34)) + convertDpToPx(37);
        final ViewGroup.MarginLayoutParams layoutParams_w = (ViewGroup.MarginLayoutParams) imageViewHorizontalDot.getLayoutParams();
        layoutParams_w.setMarginStart(leftMargin);
        imageViewHorizontalDot.setLayoutParams(layoutParams_w);

        int topMargin = (int) (doubleVal1 * convertDpToPx(34)) + convertDpToPx(170);
        final ViewGroup.MarginLayoutParams layoutParams_s = (ViewGroup.MarginLayoutParams) imageViewVerticalDot.getLayoutParams();
        layoutParams_s.setMargins(convertDpToPx(37), topMargin, 0, 0);
        imageViewVerticalDot.setLayoutParams(layoutParams_s);
    }
}