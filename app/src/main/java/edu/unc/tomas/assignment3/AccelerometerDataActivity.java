package edu.unc.tomas.assignment3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class AccelerometerDataActivity extends SensorDataActivity {

    long lastPrint;
    Integer accelerometerDataDifference;

    protected void initializeSensor () {
        accelerometerDataDifference = getResources().getInteger(R.integer.accelerometer_data_difference);
        this.senses = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        this.sensor = this.senses.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.timestamp - lastPrint >= accelerometerDataDifference) {
            Float value = new Float(Math.sqrt(
                    event.values[0] * event.values[0] + event.values[1] * event.values[1] + event.values[2] * event.values[2]));
            Float maxValue = new Float(this.sensor.getMaximumRange());
            Float percentValue = (value / maxValue) * 100;
            this.handleNewValue(percentValue, maxValue);
            lastPrint = event.timestamp;
        }
    }

}
