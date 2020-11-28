package com.lab.sensory;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DataViewer extends AppCompatActivity implements SensorEventListener {

    private SensorEventListener accEventListener;
    private SensorManager sm;
    private Sensor accSensor;

    private TextView coordX;
    private TextView coordY;
    private TextView coordZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_viewer);
        setTextViews();
        registerListener();
    }

    private void setTextViews() {
        coordX = findViewById(R.id.coordX);
        coordY = findViewById(R.id.coordY);
        coordZ = findViewById(R.id.coordZ);
    }

    private void registerListener() {
        setSensorFields();
        sm.registerListener(accEventListener, accSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private void setSensorFields() {
        if (sm == null)
            sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (accEventListener == null)
            accEventListener = this;

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

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateDisplayCoords(new Coordinates(event.values[0], event.values[1], event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //no action
    }

    private void updateDisplayCoords(Coordinates coordinates) {
        coordX.setText("X: " + coordinates.getX().toString());
        coordY.setText("Y: " + coordinates.getY().toString());
        coordZ.setText("Z: " + coordinates.getZ().toString());
    }
}