package com.appli.nyx.formx.ui.fields;

import android.content.Context;
import android.view.View;

import com.appli.nyx.formx.model.firebase.fields.AbstractQuestion;

public  interface IFieldGenerator<V extends AbstractQuestion> {

    public  View generateLayout(Context context,  V field);

    public  void loadValues(final V field);

    public boolean generateError(Context context, V field);
}
