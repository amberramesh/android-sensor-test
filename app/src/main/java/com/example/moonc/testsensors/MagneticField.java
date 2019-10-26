package com.example.moonc.testsensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MagneticField extends AppCompatActivity {

    TextView magText, magX, magY, magZ;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    DecimalFormat df;
    double magStrength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetic_field);

        magText = findViewById(R.id.magText);
        magX = findViewById(R.id.magX);
        magY = findViewById(R.id.magY);
        magZ = findViewById(R.id.magZ);

        df = new DecimalFormat("0.000");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                magX.setText(""+sensorEvent.values[0]);
                magY.setText(""+sensorEvent.values[1]);
                magZ.setText(""+sensorEvent.values[2]);

                magStrength = Math.sqrt(sensorEvent.values[0] * sensorEvent.values[0] +
                        sensorEvent.values[1] * sensorEvent.values[1] +
                        sensorEvent.values[2] * sensorEvent.values[2]);

                magText.setText(df.format(magStrength)+" \u00B5Tesla");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }
}