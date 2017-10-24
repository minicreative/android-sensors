package edu.unc.tomas.assignment3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class PlotView extends View {

    Context context;
    ArrayList<PlotItem> list;
    Float maximumValue;

    public PlotView(Context context) {
        super(context);
        this.context = context;
    }
    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    public PlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    public PlotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Make sure list is initialized
        if (list == null) this.clearList();

        // Setup paints
        Paint gridPaint = new Paint();
        gridPaint.setColor(ContextCompat.getColor(this.context, R.color.colorGrid));
        gridPaint.setStrokeWidth(3);
        gridPaint.setTextSize(36);
        Paint valuePaint = new Paint();
        valuePaint.setColor(ContextCompat.getColor(this.context, R.color.colorValue));
        valuePaint.setStrokeWidth(5);
        Paint stdDevPaint = new Paint();
        stdDevPaint.setColor(ContextCompat.getColor(this.context, R.color.colorStdDev));
        stdDevPaint.setStrokeWidth(5);
        Paint meanPaint = new Paint();
        meanPaint.setColor(ContextCompat.getColor(this.context, R.color.colorMean));
        meanPaint.setStrokeWidth(5);

        // Get width and height
        Float width = new Float(this.getWidth());
        Float height = new Float(this.getHeight());

        // Calculate scale adjustments with view size
        Float xScale = (width-20) / 4;
        Float yScale = (height-15) / 100;

        // Ensure maximumValue is not null
        if (maximumValue == null) maximumValue = new Float(100);

        // Draw grid
        Float widthIndex = new Float(0);
        while (widthIndex < width) {
            canvas.drawLine(widthIndex, 0, widthIndex, height, gridPaint);
            widthIndex += width / 4;
        }
        Float heightIndex = new Float(0);
        while (heightIndex < height) {
            canvas.drawLine(0, height-heightIndex, width, height-heightIndex, gridPaint);
            Float percent = heightIndex / height;
            Float gridValue = new Float(maximumValue * percent);
            String heightString = gridValue.toString();
            canvas.drawText(heightString, 0, height-heightIndex, gridPaint);
            heightIndex += height / 6;
        }

        // Draw each point in array relative to view size
        for (int i=0; i < list.size(); i++) {
            Float floatIndex = new Float(i);
            PlotItem item = list.get(i);
            canvas.drawCircle(floatIndex*xScale+10, height-(item.getStdDev()*yScale+10), 10, stdDevPaint);
            canvas.drawCircle(floatIndex*xScale+10, height-(item.getMean()*yScale+10), 10, meanPaint);
            canvas.drawCircle(floatIndex*xScale+10, height-(item.getValue()*yScale+10), 10, valuePaint);

            // If greater than one, draw line to previous
            if (i > 0) {
                Float prevFloatIndex = new Float(i-1);
                PlotItem prevItem = list.get(i-1);
                canvas.drawLine(prevFloatIndex*xScale+10, height-(prevItem.getStdDev()*yScale+10),
                        floatIndex*xScale+10, height-(item.getStdDev()*yScale+10), stdDevPaint);
                canvas.drawLine(prevFloatIndex*xScale+10, height-(prevItem.getMean()*yScale+10),
                        floatIndex*xScale+10, height-(item.getMean()*yScale+10), meanPaint);
                canvas.drawLine(prevFloatIndex*xScale+10, height-(prevItem.getValue()*yScale+10),
                        floatIndex*xScale+10, height-(item.getValue()*yScale+10), valuePaint);
            }
        }
    };

    public void clearList() {

        // Set list equal to empty array of size 5
        list = new ArrayList<PlotItem>(5);
    };

    public void addPlotItem(PlotItem item) {

        // Make sure list is initialized
        if (list == null) this.clearList();

        // Remove the first element if the list is full
        if (list.size() == 5) list.remove(0);

        // Add the plot item
        list.add(item);
    };

    public void setMaximumValue (float maximumValue) {
        this.maximumValue = maximumValue;
    };

}