package edu.unc.tomas.assignment3;

import android.util.Log;

import java.util.ArrayList;

public class Stats {

    public static float getStdDev(ArrayList<Float> values, Float mean) {
        float stdDev = new Float(0);
        for(float num: values) {
            stdDev += Math.pow(num - mean, 2);
        }
        return new Float(Math.sqrt(stdDev/values.size()));
    }

    public static float getMean(ArrayList<Float> values) {
        float sum = new Float(0);
        for (float num : values) sum += num;
        float mean = sum/values.size();
        return mean;
    }

}
