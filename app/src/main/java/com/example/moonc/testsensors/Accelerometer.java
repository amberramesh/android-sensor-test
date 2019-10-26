package com.example.moonc.testsensors;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Accelerometer extends AppCompatActivity {

    LinearLayout accelLayout;
    TextView accelText, accelX, accelY, accelZ;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        accelLayout = findViewById(R.id.accelLayout);
        accelText = findViewById(R.id.accelText);
        accelX = findViewById(R.id.accelX);
        accelY = findViewById(R.id.accelY);
        accelZ = findViewById(R.id.accelZ);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                accelText.setText("Orientation");

                // Determining orientation along X and Y axes
                if(Math.abs(sensorEvent.values[0]) > Math.abs(sensorEvent.values[1])) {
                    if (sensorEvent.values[0] > 0.0f) {
                        accelLayout.setRotation(90.0f);
                        accelText.setText("On the Left Edge");
                    }
                    else {
                        accelLayout.setRotation(270.0f);
                        accelText.setText("On the Right Edge");
                    }
                } else {
                    if(sensorEvent.values[1] > 0.0f) {
                        accelLayout.setRotation(0.0f);
                        accelText.setText("Upright");
                    }
                    else {
                        accelLayout.setRotation(180.0f);
                        accelText.setText("Upside Down");
                    }
                }

                // Test for Z axis
                if(Math.abs(sensorEvent.values[2]) > Math.abs(sensorEvent.values[0]) &&
                        Math.abs(sensorEvent.values[2]) > Math.abs(sensorEvent.values[1])) {
                    if(sensorEvent.values[2] > 0.0f)
                        accelText.setText("Face Up");
                    else
                        accelText.setText("Face Down");
                }

                // Setting table values
                accelX.setText(""+sensorEvent.values[0]);
                accelY.setText(""+sensorEvent.values[1]);
                accelZ.setText(""+sensorEvent.values[2]);

            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
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
