package com.lab.sensory;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DataViewer extends AppCompatActivity {

    private SensorEventListener accEventListener;
    private SensorManager sm;
    private Sensor accSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_viewer);
        registerListener();
    }

    private void registerListener() {
        setSensorFields();
        sm.registerListener(accEventListener, accSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private void setSensorFields() {
        if (sm == null)
            sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (accEventListener == null)
            accEventListener = new AccelerometerEventListener();

        if (accSensor == null)
            accSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterListener();
    }

    private void unregisterListener() {
        sm.unregisterListener(accEventListener);
    }
}