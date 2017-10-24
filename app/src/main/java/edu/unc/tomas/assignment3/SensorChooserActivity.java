package edu.unc.tomas.assignment3;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SensorChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_chooser);

        // Initialize manager and sensors
        SensorManager senses = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        Sensor acceleromater = senses.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor light = senses.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Initialize status areas
        TextView acceleromaterStatus = (TextView) findViewById(R.id.accelerometerStatus);
        TextView lightStatus = (TextView) findViewById(R.id.lightSensorStatus);

        // Setup status
        setupStatus(acceleromater, acceleromaterStatus);
        setupStatus(light, lightStatus);
    }

    public void startAccelerometer (View view) {
        Intent intent = new Intent(this, AccelerometerDataActivity.class);
        startActivity(intent);
    }

    public void startLightSensor (View view) {
        Intent intent = new Intent(this, LightSensorDataActivity.class);
        startActivity(intent);
    }

    public void startAccelerometerAnimation (View view) {
        Intent intent = new Intent(this, AccelerometerAnimationActivity.class);
        startActivity(intent);
    }

    public void startLightSensorAnimation (View view) {
        Intent intent = new Intent(this, LightSensorAnimationActivity.class);
        startActivity(intent);
    }

    private void setupStatus(Sensor sensor, TextView view) {
        String status = "";

        // Check if available
        if (sensor == null) {
            status += "Status: Not available";
        }

        // If available, update status, add info
        else {
            status += "Status: Available";
            status += "\nVendor: " + sensor.getVendor();
            status += "\nRange: " + Float.toString(sensor.getMaximumRange());
            status += "\nResolution: " + Float.toString(sensor.getResolution());
            status += "\nMinimum Delay: " + Integer.toString(sensor.getMinDelay());
        }

        // Set text
        view.setText(status);
    }
}
