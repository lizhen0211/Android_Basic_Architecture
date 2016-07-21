package com.architecture.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.architecture.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by lz on 2016/4/11.
 */
public class DatePickerDialog extends Dialog implements View.OnClickListener {

    private MaterialCalendarView mCalendarView;

    private TextView confirmBtn;

    private TextView cancelBtn;

    private final Calendar mCalendar;

    private final OnDateSetListener mOnDateSetListener;

    public interface OnDateSetListener {

        void onDateSet(MaterialCalendarView view, int year, int monthOfYear, int dayOfMonth);
    }

    public DatePickerDialog(Context context, OnDateSetListener listener, int theme) {
        super(context, theme);
        mCalendar = Calendar.getInstance();
        mOnDateSetListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker_dialog);
        mCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        mCalendarView.state().edit().setMaximumDate(mCalendar.getTime());
        mCalendarView.setSelectedDate(mCalendar.getTime());
        mCalendarView.setTitleFormatter(new DateFormatTitleFormatter(new SimpleDateFormat("yyyy年MM月", Locale.getDefault())));
        mCalendarView.setDynamicHeightEnabled(true);
        confirmBtn = (TextView) findViewById(R.id.confirm);
        cancelBtn = (TextView) findViewById(R.id.cancel);
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if (mOnDateSetListener != null) {
                    CalendarDay currentDate = mCalendarView.getCurrentDate();
                    mOnDateSetListener.onDateSet(mCalendarView, currentDate.getYear(),
                            currentDate.getMonth(), currentDate.getDay());

                    dismiss();
                }
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }
}
