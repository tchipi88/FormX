package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Enquete;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class EnqueteViewModel extends AndroidViewModel {

    private MutableLiveData<Enquete> enqueteMutableLiveData = new MutableLiveData<>();

    @Inject
    public EnqueteViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Enquete>> loadEnqueteByUser() {
        List<Enquete> enquetes= Arrays.asList(new Enquete("Enquete  1"),
                new Enquete("Enquete  2"),
                new Enquete("Enquete  3"),
                new Enquete("Enquete  4"),
                new Enquete("Enquete  5"),
        new Enquete("Enquete  6"));
        return  new MutableLiveData<>(enquetes);
    }

    public void setEnquete(Enquete enquete) {
        enqueteMutableLiveData.setValue(enquete);
    }
}
