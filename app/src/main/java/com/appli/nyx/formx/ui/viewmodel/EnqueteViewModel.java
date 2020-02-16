package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Enquete;

import javax.inject.Inject;

public class EnqueteViewModel extends AndroidViewModel {

    private MutableLiveData<Enquete> enqueteMutableLiveData = new MutableLiveData<>();

    @Inject
    public EnqueteViewModel(@NonNull Application application) {
        super(application);
    }


    public void setEnquete(Enquete enquete) {
        enqueteMutableLiveData.setValue(enquete);
    }

    public MutableLiveData<Enquete> getEnqueteMutableLiveData() {
        return enqueteMutableLiveData;
    }
}
