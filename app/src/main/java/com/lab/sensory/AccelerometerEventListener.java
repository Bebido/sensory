package com.lab.sensory;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class AccelerometerEventListener implements SensorEventListener {



    @Override
    public void onSensorChanged(SensorEvent event) {
        new Coordinates(event.values[0], event.values[1], event.values[2]);
        int a = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        int b = 0;
    }
}
