package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.view.View;

import com.appli.nyx.formx.model.firebase.fields.DateQuestion;

public class DateFieldGenerator implements  IFieldGenerator<DateQuestion>{

    @Override
    public View generateLayout(Context context, DateQuestion field) {
        return null;
    }

    @Override
    public void loadValues(DateQuestion field) {

    }

    @Override
    public void generateError(DateQuestion field) {

    }
}
