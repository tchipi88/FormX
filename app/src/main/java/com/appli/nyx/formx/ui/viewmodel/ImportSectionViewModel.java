package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Form;

import javax.inject.Inject;

public class ImportSectionViewModel extends AndroidViewModel {

    private MutableLiveData<Form> formMutableLiveData = new MutableLiveData<>();

    @Inject
    public ImportSectionViewModel(@NonNull Application application) {
        super(application);
    }

    public void setForm(Form form) {
        formMutableLiveData.setValue(form);
    }

    public MutableLiveData<Form> getFormMutableLiveData() {
        return formMutableLiveData;
    }
}
