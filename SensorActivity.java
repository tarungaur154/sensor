package com.example.studentregistrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    // it is system service. stores data of all hardware sensors
    // it has info of all sensors, whether or not your device has it or not
    // kiosk devices don't have serviceManager
    // we customize the android as per your device, like kiosk devices dont need sensors, so they are not given sensorManager
    private Sensor accelerometerSensor, sensor;

    TextView tvSensorAcc, tvSensorProx, tvSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        tvSensorAcc = findViewById(R.id.tvSensorAcc);
        tvSensorProx = findViewById(R.id.tvSensorProx);
        tvSensor = findViewById(R.id.tvSensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager != null){
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

            if(accelerometerSensor != null){
                sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else{
                tvSensorProx.setText("No ACCELEROMETER sensor found!");
            }

            if(sensor != null){
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else{
                tvSensorAcc.setText("No PROXIMITY sensor found!");
            }
        } else{
            tvSensor.setText("No sensor service available!");
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x, y, z;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            tvSensorAcc.setText("ACCELEROMETER values : " + String.format("x: %.2f y: %.2f z: %.2f", x, y, z));
            // ... -> Virtual Sensors -> Device Pose -> [change x, y, z]
        }

        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            float x, y, z;
            x = event.values[0];

            tvSensorProx.setText("PROXIMITY values : " + String.format("x: %.2f", x));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(accelerometerSensor != null){
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else{
            tvSensorProx.setText("No ACCELEROMETER sensor found!");
        }

        if(sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else{
            tvSensorAcc.setText("No PROXIMITY sensor found!");
        }
    }

    /*

    Application class
    main config class of app
    <android:name> -> app name

    anything inside is additional to exisitng config
     */
}