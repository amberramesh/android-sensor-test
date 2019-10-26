/*
*  Test Sensors
*  Written by Amber Ramesh
* */

package com.example.moonc.testsensors;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    Spinner selectSensor;
    Button submitSensor;
    ArrayList<String> sensors;
    AlertDialog.Builder alertDialog;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectSensor = findViewById(R.id.selectSensor);
        submitSensor = findViewById(R.id.submitSensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        alertDialog = new AlertDialog.Builder(Main.this);
        alertDialog.setIcon(android.R.drawable.stat_notify_error);
        alertDialog.setTitle("Error");
        alertDialog.setMessage("This sensor is not supported by your device.");
        alertDialog.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        sensors = new ArrayList<>();
        sensors.add("Accelerometer");
        sensors.add("Ambient Temperature");
        sensors.add("Magnetometer");
        sensors.add("Gyroscope");
        sensors.add("Heart Rate Sensor");
        sensors.add("Light Meter");
        sensors.add("Proximity Sensor");
        sensors.add("Barometer");
        sensors.add("Relative Humidity");

        ArrayAdapter<String> ad = new ArrayAdapter<>(Main.this, R.layout.support_simple_spinner_dropdown_item, sensors);
        selectSensor.setAdapter(ad);

        submitSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (selectSensor.getSelectedItemPosition()) {
                    case 0: if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
                        startActivity(new Intent(Main.this, Accelerometer.class));
                        else
                            alertDialog.show();
                        break;
                    case 1: if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null)
                        startActivity(new Intent(Main.this, AmbientTemperature.class));
                    else
                        alertDialog.show();
                        break;
                    case 2: if(sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
                        startActivity(new Intent(Main.this, MagneticField.class));
                    else
                        alertDialog.show();
                        break;
                    case 3: if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null)
                        startActivity(new Intent(Main.this, Gyroscope.class));
                    else
                        alertDialog.show();
                        break;
                    case 4: if(sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null)
                        startActivity(new Intent(Main.this, HeartRate.class));
                    else
                        alertDialog.show();
                        break;
                    case 5: if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null)
                        startActivity(new Intent(Main.this, Light.class));
                    else
                        alertDialog.show();
                        break;
                    case 6: if(sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null)
                        startActivity(new Intent(Main.this, Proximity.class));
                    else
                        alertDialog.show();
                        break;
                    case 7: if(sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null)
                        startActivity(new Intent(Main.this, Pressure.class));
                    else
                        alertDialog.show();
                        break;
                    case 8: if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null)
                        startActivity(new Intent(Main.this, RelativeHumidity.class));
                    else
                        alertDialog.show();
                        break;
                }
            }
        });
    }
}
