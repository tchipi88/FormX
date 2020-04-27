package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.EnqueteForm;

import java.util.List;

import javax.inject.Inject;

public class SelectFormViewModel extends AndroidViewModel {

    private MutableLiveData<List<EnqueteForm>> formMutableLiveData = new MutableLiveData<>();

    @Inject
    public SelectFormViewModel(@NonNull Application application) {
        super(application);
    }

    public void setForms(List<EnqueteForm> enqueteForms) {
        formMutableLiveData.setValue(enqueteForms);
    }

    public MutableLiveData<List<EnqueteForm>> getFormsMutableLiveData() {
        return formMutableLiveData;
    }
}
