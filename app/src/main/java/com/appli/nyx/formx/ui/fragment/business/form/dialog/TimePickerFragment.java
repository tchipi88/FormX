package com.appli.nyx.formx.ui.fragment.business.form.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.appli.nyx.formx.utils.DateUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.joda.time.LocalTime;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    final TextInputEditText edtInput;

    public TimePickerFragment(TextInputEditText edtInput) {
        this.edtInput = edtInput;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    public void onTimeSet(TimePicker view, int hour, int minute) {
        edtInput.setText(DateUtils.getStringTime(new LocalTime(hour, minute)));
    }
}
