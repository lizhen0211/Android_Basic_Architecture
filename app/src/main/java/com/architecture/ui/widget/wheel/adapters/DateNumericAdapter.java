package com.architecture.ui.widget.wheel.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by lz on 2016/4/6.
 */
public class DateNumericAdapter extends NumericWheelAdapter {


    //unit
    private String unit;

    //textHeight
    private int textHeight;

    // Index of current item
    private int currentItem;

    public DateNumericAdapter(Context context) {
        super(context);
    }

    public DateNumericAdapter(Context context, int minValue, int maxValue) {
        super(context, minValue, maxValue);
    }

    public DateNumericAdapter(Context context, int minValue, int maxValue, String format) {
        super(context, minValue, maxValue, format);
    }

    public DateNumericAdapter(Context context, int minValue, int maxValue, String format, String unit) {
        this(context, minValue, maxValue, format);
        this.unit = unit;
    }

//    @Override
//    public CharSequence getItemText(int index) {
//        CharSequence itemText = super.getItemText(index);
//        return unit == null ? itemText : itemText + unit;
//    }

    @Override
    protected void configureTextView(TextView view) {
        super.configureTextView(view);
        if (textHeight > 0) {
            view.setHeight(textHeight);
        }

        view.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        view.setText(unit == null ? view.getText() : view.getText() + unit);
    }


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(int textHeight) {
        this.textHeight = textHeight;
    }
}
