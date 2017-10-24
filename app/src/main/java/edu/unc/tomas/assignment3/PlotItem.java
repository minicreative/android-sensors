package edu.unc.tomas.assignment3;

/**
 * Created by tomasroy on 10/13/17.
 */

public class PlotItem {

    private float value;
    private float stdDev;
    private float mean;

    public PlotItem (float value) {
        this.value = value;
        this.stdDev = 0;
        this.mean = 0;
    }

    public PlotItem (float value, float stdDev, float mean) {
        this.value = value;
        this.stdDev = stdDev;
        this.mean = mean;
    }

    public float getValue () {
        return this.value;
    }
    public float getStdDev () {
        return this.stdDev;
    }
    public float getMean () {
        return this.mean;
    }
}
