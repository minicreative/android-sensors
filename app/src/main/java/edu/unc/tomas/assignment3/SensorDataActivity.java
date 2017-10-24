package edu.unc.tomas.assignment3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

public abstract class SensorDataActivity extends AppCompatActivity implements SensorEventListener{

    Integer sensorDataDelay;
    SensorManager senses;
    Sensor sensor;
    ArrayList<Float> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sensorDataDelay = getResources().getInteger(R.integer.sensor_data_delay);
        this.initializeSensor();
    }

    // Functionality
    protected void handleNewValue (Float value, Float maximumValue) {

        // Get plot view with ID
        PlotView plotView = (PlotView) findViewById(R.id.plot);

        // Set maximum value
        plotView.setMaximumValue(maximumValue);

        // Make sure values array is initiated
        if (values == null) values = new ArrayList<Float>(5);

        // Push value into values (make sure only 5 are stored)
        if (values.size() == 5) values.remove(0);
        values.add(value);

        // Calculate mean and stdev
        Float mean = Stats.getMean(values);
        Float stdDev = Stats.getStdDev(values, mean);

        // Create new PlotItem
        PlotItem item = new PlotItem(value, stdDev, mean);

        // Add item to plot view, revalidate
        plotView.addPlotItem(item);
        plotView.invalidate();
    }

    // Abstract methods
    protected abstract void initializeSensor();
    @Override
    public abstract void onSensorChanged(SensorEvent event);

    // SensorEventListener methods
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
        senses.registerListener(this, sensor, sensorDataDelay);
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
