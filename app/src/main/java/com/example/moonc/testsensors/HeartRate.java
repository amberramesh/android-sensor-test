package com.example.moonc.testsensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HeartRate extends AppCompatActivity {

    TextView heartText;
    ProgressBar heartBar;
    Button heartStart, heartReset;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    ArrayList<Integer> heartRate;
    int sensoryAccuracy;
    int progressCount, averageRate;
    boolean canStart;

    private static final String TAG = "HeartRateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        heartText = findViewById(R.id.heartText);
        heartBar = findViewById(R.id.heartBar);
        heartStart = findViewById(R.id.heartStart);
        heartReset = findViewById(R.id.heartReset);

        progressCount = 0;
        heartBar.setProgress(progressCount);
        heartRate = new ArrayList<>();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Log.d(TAG, String.valueOf(sensorEvent.values[0]));
                if(sensoryAccuracy > 0 && !canStart)
                    heartText.setText("Finger Set");
                if(sensoryAccuracy > 0 && progressCount < 30 && canStart) {
                    heartText.setText(String.valueOf((int)sensorEvent.values[0]));
                    heartBar.setProgress(progressCount++);
                    heartRate.add((int)sensorEvent.values[0]);
                }
                if(sensoryAccuracy < 1)
                    heartText.setText("Finger Not Set");
                if(progressCount == 30) {
                    averageRate = 0;
                    for(int i : heartRate)
                        averageRate += i;
                    averageRate /= 30;
                    heartText.setText("Average Heart Rate: "+averageRate);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                sensoryAccuracy = i;
            }
        };

        heartStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canStart = true;
            }
        });

        heartReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canStart = false;
                averageRate = 0;
                progressCount = 0;
                heartRate.clear();
                heartBar.setProgress(progressCount);
                if(sensoryAccuracy > 0)
                    heartText.setText("Finger Set");
                else
                    heartText.setText("Finger Not Set");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }
}