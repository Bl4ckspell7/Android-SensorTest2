package com.example.sensortest2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sensortest2.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor magneticFieldSensor;
    Sensor accelerometerSensor;
    Sensor proximitySensor;
    Sensor lightSensor;
    ActivityMainBinding binding;
    VibratorManager vibratorManager;
    Vibrator vibrator;
    private final static int VIBRATION_DURATION = 120;
    private static final int ONEEIGHTY = 180;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    private double proximitySensorValue;
    private float lightSensorValue;
    private final int[] sensorData = new int[5];

    FragmentManager fragmentManager;
    private final Fragment tab1Fragment = new SensorTabFragment1();
    private final Fragment tab2Fragment = new SensorTabFragment2();
    private final Fragment tab3Fragment = new SensorTabFragment3();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentManager = getSupportFragmentManager();
        replaceFragment(tab1Fragment);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tab1) {
                replaceFragment(tab1Fragment);
            } else if (itemId == R.id.tab2) {
                replaceFragment(tab2Fragment);
            } else if (itemId == R.id.tab3) {
                replaceFragment(tab3Fragment);
            }
            return true;
        });
        attachSensors();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void attachSensors() {
        vibratorManager = (VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
        vibrator = vibratorManager.getDefaultVibrator();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    public void vibrate() {
        vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(sensorEvent.values, 0, accelerometerReading, 0, accelerometerReading.length);
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(sensorEvent.values, 0, magnetometerReading, 0, magnetometerReading.length);
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximitySensorValue = sensorEvent.values[0];
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightSensorValue = sensorEvent.values[0];
        }
        updateOrientationAngles();
        setValues();
        updateFragmentData();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometerSensor != null)
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        if (magneticFieldSensor != null)
            sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        if (proximitySensor != null)
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (lightSensor != null)
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void updateOrientationAngles() {
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
    }

    private void setValues() {
        //Convert radians to degrees
        sensorData[0] = (int) (-orientationAngles[0] * ONEEIGHTY / Math.PI); // orientation value
        sensorData[1] = (int) (orientationAngles[1] * ONEEIGHTY / Math.PI); // vertical value
        sensorData[2] = (int) (orientationAngles[2] * ONEEIGHTY / Math.PI); // horizontal value
        sensorData[3] = (int) proximitySensorValue;
        sensorData[4] = (int) lightSensorValue;
    }


    private void updateFragmentData() {
        SensorTabFragment sensorTabFragment = (SensorTabFragment) fragmentManager.findFragmentById(R.id.frameLayout);
        assert sensorTabFragment != null;
        sensorTabFragment.setData(sensorData);
    }

}