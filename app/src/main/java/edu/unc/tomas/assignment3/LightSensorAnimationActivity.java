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

public class LightSensorAnimationActivity extends AppCompatActivity implements SensorEventListener{

    Integer lightAnimationDelay;
    Integer lightAnimationDifference;
    Integer lightAnimationThreshold;

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
        lightAnimationDelay = getResources().getInteger(R.integer.light_animation_delay);
        lightAnimationDifference = getResources().getInteger(R.integer.light_animation_difference);
        lightAnimationThreshold = getResources().getInteger(R.integer.light_animation_threshold);

        // Initialize sensor
        senses = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        sensor = senses.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Initialize values
        values = new ArrayList<Float>(5);

        // Initialize view elements
        imageFrame = findViewById(R.id.imageFrame);
        status = (TextView) findViewById(R.id.status);

        // Initialize threshold
        threshold = new Float(lightAnimationThreshold);
    }

    // SensorEventListener methods
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.timestamp - lastPrint >= lightAnimationDifference) {

            // Get value
            Float value = new Float (event.values[0]);
            lastPrint = event.timestamp;

            // Push value into values (make sure only 5 are stored)
            if (values.size() == 5) values.remove(0);
            values.add(value);

            // Calculate mean
            Float mean = Stats.getMean(values);

            // Update background based on mean and threshold
            if (mean < threshold) {
                imageFrame.setBackgroundResource(R.drawable.lightoff);
            } else {
                imageFrame.setBackgroundResource(R.drawable.lighton);
            }

            // Update status
            status.setText("Mean: "+mean.toString()+" / Threshold: "+threshold.toString());
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
        senses.registerListener(this, sensor, lightAnimationDelay);
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
