package edu.unc.tomas.assignment3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class AccelerometerAnimationActivity extends AppCompatActivity implements SensorEventListener{

    Integer accelerometerAnimationDifference;
    Integer accelerometerAnimationDelay;
    Integer accelerometerAnimationThreshold;

    SensorManager senses;
    Sensor sensor;
    ArrayList<Float> values;
    View imageFrame;
    TextView status;
    long lastPrint;
    Float threshold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_animation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize integers
        accelerometerAnimationDifference = getResources().getInteger(R.integer.accelerometer_animation_difference);
        accelerometerAnimationDelay = getResources().getInteger(R.integer.accelerometer_animation_delay);
        accelerometerAnimationThreshold = getResources().getInteger(R.integer.accelerometer_animation_threshold);

        // Initialize sensor
        senses = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        sensor = senses.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize values
        values = new ArrayList<Float>(5);

        // Initialize view elements
        imageFrame = findViewById(R.id.imageFrame);
        status = (TextView) findViewById(R.id.status);

        // Initialize threshold
        threshold = new Float(accelerometerAnimationThreshold);
    }

    // SensorEventListener methods
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.timestamp - lastPrint >= accelerometerAnimationDifference) {

            // Get value
            Float value = new Float(Math.sqrt(
                    event.values[0] * event.values[0] + event.values[1] * event.values[1] + event.values[2] * event.values[2]));
            lastPrint = event.timestamp;

            // Push value into values (make sure only 5 are stored)
            if (values.size() == 5) values.remove(0);
            values.add(value);

            // Calculate mean
            Float mean = Stats.getMean(values);
            Float stdDev = Stats.getStdDev(values, mean);

            // Update background based on mean and threshold
            if (stdDev > threshold) {
                imageFrame.setBackgroundResource(R.drawable.eggcracked);
            } else {
                imageFrame.setBackgroundResource(R.drawable.egg);
            }

            // Update status
            status.setText("StdDev: "+stdDev.toString()+" / Threshold: "+threshold.toString());
        }

    };
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onPause() {
        super.onPause();
        senses.unregisterListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        senses.registerListener(this, sensor, accelerometerAnimationDelay);
    }

    // Makes back button function
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
