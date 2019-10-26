package com.example.moonc.testsensors;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Gyroscope extends AppCompatActivity {

    TextView gyroX, gyroY, gyroZ;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    MediaPlayer mp;
    Vibrator v;

    float lastX, lastY, lastZ;
    long vibrateTime = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        gyroX = findViewById(R.id.gyroX);
        gyroY = findViewById(R.id.gyroY);
        gyroZ = findViewById(R.id.gyroZ);

        lastX = lastY = lastZ = 0.0f;

        mp = MediaPlayer.create(Gyroscope.this, R.raw.ding);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if(Math.abs(lastX - sensorEvent.values[0]) > 5.0f ||
                        Math.abs(lastY - sensorEvent.values[1]) > 5.0f ||
                        Math.abs(lastZ - sensorEvent.values[2]) > 5.0f) {

                   if(System.currentTimeMillis() - vibrateTime > 2000) {
                       // Play audio
                       mp.seekTo(1000);
                       mp.start();
                       // Vibrate
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                           v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                       } else {
                           v.vibrate(500);
                       }
                       // Set vibrate time to prevent excess vibration
                       vibrateTime = System.currentTimeMillis();
                   }
                }

                lastX = sensorEvent.values[0];
                lastY = sensorEvent.values[1];
                lastZ = sensorEvent.values[2];

                gyroX.setText(""+sensorEvent.values[0]);
                gyroY.setText(""+sensorEvent.values[1]);
                gyroZ.setText(""+sensorEvent.values[2]);
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
