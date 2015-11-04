package com.itacit.healthcare.presentation.base.widgets.datePicker;

import android.content.Context;

/**
 * Created by root on 04.11.15.
 */
public class DatePickerCancelDialog extends android.app.DatePickerDialog {
    public DatePickerCancelDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }


}
