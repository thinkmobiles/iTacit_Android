package com.itacit.healthcare.presentation.base.widgets.datePicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import static android.app.DatePickerDialog.OnDateSetListener;
import static android.content.DialogInterface.*;

/**
 * Created by root on 04.11.15.
 */
public class DatePickerFragment extends DialogFragment {
    private OnDateSetListener dateSetListener;
    private OnDateClearListener dateClearListener;
    public DatePickerFragment(OnDateSetListener dateSetListener, OnDateClearListener dateClearListener) {
        super();
        this.dateSetListener = dateSetListener;
        this.dateClearListener = dateClearListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerCancelDialog dialog = new DatePickerCancelDialog(getActivity(), dateSetListener, year, month, day);
        dialog.setButton(BUTTON_POSITIVE, ("Done"), dialog);
        dialog.setButton(BUTTON_NEUTRAL, ("Clear"), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dateClearListener.onDateClear();
            }
        });
        dialog.setButton(BUTTON_NEGATIVE, ("Cancel"), dialog);
        return dialog;
    }



    public interface OnDateClearListener {
        void onDateClear();
    }
}
