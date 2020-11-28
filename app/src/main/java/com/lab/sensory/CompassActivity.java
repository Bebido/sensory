package com.lab.sensory;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private CompassView compassView;
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    float[] accelerometerValues;
    float[] magnetometerValues;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compassView = new CompassView(this);
        setContentView(compassView);
        registerListeners();
    }

    protected void onResume() {
        super.onResume();
        registerListeners();
    }

    private void registerListeners() {
        setSensors();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void setSensors() {
        if (mSensorManager == null || accelerometer == null || magnetometer == null) {
            mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
    }

    protected void onPause() {
        super.onPause();
        unregisterListeners();
    }

    private void unregisterListeners() {
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    public void onSensorChanged(SensorEvent event) {
        setSensorValues(event);
        if (allValuesPresent()) {
            float[] R = new float[9];
            float[] I = new float[9];
            if (SensorManager.getRotationMatrix(R, I, accelerometerValues, magnetometerValues))
                updatePosition(R);
        }
    }

    private void setSensorValues(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelerometerValues = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magnetometerValues = event.values;
    }

    private boolean allValuesPresent() {
        return accelerometerValues != null && magnetometerValues != null;
    }

    private void updatePosition(float[] R) {
        float[] orientation = new float[3];
        SensorManager.getOrientation(R, orientation);
        compassView.azimuth = orientation[0];
        compassView.invalidate();
    }
}