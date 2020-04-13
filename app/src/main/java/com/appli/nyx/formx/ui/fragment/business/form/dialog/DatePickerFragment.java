package com.appli.nyx.formx.ui.fragment.business.form.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.appli.nyx.formx.utils.DateUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.joda.time.LocalDate;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    final TextInputEditText edtInput;

    public DatePickerFragment(TextInputEditText edtInput) {
        this.edtInput = edtInput;
    }


    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        edtInput.setText(DateUtils.getStringDate(new LocalDate(year, month + 1, day)));
    }
}