package edu.unc.tomas.assignment3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class LightSensorDataActivity extends SensorDataActivity {

    Integer lightDataDifference;
    Integer lightDataMax;

    long lastPrint;

    protected void initializeSensor () {
        lightDataDifference = getResources().getInteger(R.integer.light_data_difference);
        lightDataMax = getResources().getInteger(R.integer.light_data_max);
        this.senses = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        this.sensor = this.senses.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.timestamp - lastPrint >= lightDataDifference) {
            Float value = new Float (event.values[0]);
            Float maxValue = new Float(lightDataMax); // Max is 27000 but this was never remotely reached
            Float percentValue = (value / maxValue) * 100;
            this.handleNewValue(percentValue, maxValue);
            lastPrint = event.timestamp;
        }
    }

}
