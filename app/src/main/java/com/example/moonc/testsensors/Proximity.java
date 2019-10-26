package com.example.moonc.testsensors;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Proximity extends AppCompatActivity {

    LinearLayout proxLayout;
    TextView proxText;

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    TransitionDrawable transitionDrawable;
    ValueAnimator colorAnimation;

    private static boolean startFalseChange;
    private static final String TAG = "ProximitySensor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        proxLayout = findViewById(R.id.proxLayout);
        proxText = findViewById(R.id.proxText);

        startFalseChange = false;

        transitionDrawable = (TransitionDrawable) proxLayout.getBackground();
        transitionDrawable.resetTransition();

        Integer colorDark = getResources().getColor(R.color.colorPrimaryDark);
        Integer colorLight = getResources().getColor(R.color.colorText);
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorDark, colorLight);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                proxText.setTextColor((Integer)animator.getAnimatedValue());
            }

        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (sensorEvent.values[0] == 0) {
                    transitionDrawable.startTransition(250);
                    colorAnimation.start();
                    proxText.setText("Something's nearby!");
                    if(!startFalseChange)
                        startFalseChange = true;
                } else {
                    if(startFalseChange) {
                        transitionDrawable.reverseTransition(250);
                        colorAnimation.reverse();
                        proxText.setText("Nothing detected");
                    } else
                        startFalseChange = true;
                }
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

