package com.architecture.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.architecture.R;
import com.architecture.ui.widget.wheel.OnWheelChangedListener;
import com.architecture.ui.widget.wheel.OnWheelClickedListener;
import com.architecture.ui.widget.wheel.WheelView;
import com.architecture.ui.widget.wheel.adapters.DateNumericAdapter;

import java.util.Calendar;


/**
 * Created by lz on 2016/4/7.
 */
public class MonthPickerDialog extends Dialog implements View.OnClickListener, OnWheelClickedListener, OnWheelChangedListener {

    private final Calendar mCalendar;

    private final OnMonthSetListener mMonthSetListener;

    private WheelView monthPicker;

    private WheelView yearPicker;

    private TextView confirmBtn;

    private TextView cancelBtn;

    private Context context;

    private static final int LIMIT_YEAR = 2016;

    public interface OnMonthSetListener {

        void onMonthSet(int year, int monthOfYear);
    }

    public MonthPickerDialog(Context context, OnMonthSetListener listener, int theme) {
        super(context, theme);
        mCalendar = Calendar.getInstance();
        mMonthSetListener = listener;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_picker_dialog);

        monthPicker = (WheelView) findViewById(R.id.month);
        yearPicker = (WheelView) findViewById(R.id.year);
        initYearWheelView();
        initMonthWheelView();

        confirmBtn = (TextView) findViewById(R.id.confirm);
        cancelBtn = (TextView) findViewById(R.id.cancel);
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    private void initYearWheelView() {
        int curYear = mCalendar.get(Calendar.YEAR);
        int maxYear = curYear < LIMIT_YEAR ? LIMIT_YEAR : curYear;
        int minYear = LIMIT_YEAR;

        DateNumericAdapter viewAdapter = new DateNumericAdapter(getContext(), minYear, maxYear, "%d", "年");
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 7, getContext().getResources().getDisplayMetrics());
        float textHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getContext().getResources().getDisplayMetrics());
        viewAdapter.setTextSize((int) textSize);
        viewAdapter.setTextHeight((int) textHeight);
        yearPicker.setViewAdapter(viewAdapter);
        yearPicker.setCyclic(false);
        yearPicker.setCurrentItem(viewAdapter.getItemsCount() - 1);
        yearPicker.setVisibleItems(viewAdapter.getItemsCount() >= 3 ? 3 : 1);
        yearPicker.addChangingListener(this);
        yearPicker.addClickingListener(this);
    }

    private void initMonthWheelView() {
        int maxMonth = mCalendar.getActualMaximum(Calendar.MONTH) + 1;
        int minMonth = 1;
        int curMonthIndex = mCalendar.get(Calendar.MONTH);
        DateNumericAdapter viewAdapter = new DateNumericAdapter(getContext(), minMonth, maxMonth, "%02d", "月");
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6, getContext().getResources().getDisplayMetrics());
        float textHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getContext().getResources().getDisplayMetrics());
        viewAdapter.setTextSize((int)textSize);
        viewAdapter.setTextHeight((int)textHeight);

        monthPicker.setViewAdapter(viewAdapter);
        monthPicker.setCyclic(true);
        monthPicker.setVisibleItems(5);
        monthPicker.setCurrentItem(curMonthIndex);
        monthPicker.addChangingListener(this);
        monthPicker.addClickingListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if (mMonthSetListener != null) {
                    CharSequence yearText = ((DateNumericAdapter) yearPicker.getViewAdapter()).getItemText(yearPicker.getCurrentItem());
                    int year = Integer.parseInt(yearText.toString());
                    CharSequence monthOfYearText = ((DateNumericAdapter) monthPicker.getViewAdapter()).getItemText(monthPicker.getCurrentItem());
                    int monthOfYear = Integer.parseInt(monthOfYearText.toString());
                    mMonthSetListener.onMonthSet(year, monthOfYear);
                    dismiss();
                }
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        switch (wheel.getId()) {
            case R.id.month:
                break;
            case R.id.year:
                break;
        }
    }

    @Override
    public void onItemClicked(WheelView wheel, int itemIndex) {
        switch (wheel.getId()) {
            case R.id.month:
                wheel.setCurrentItem(itemIndex, true);
                break;
            case R.id.year:
                wheel.setCurrentItem(itemIndex, true);
                break;
        }
    }
}
