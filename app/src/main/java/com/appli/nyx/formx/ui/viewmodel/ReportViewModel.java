package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Report;

import javax.inject.Inject;

public class ReportViewModel extends AndroidViewModel {

    private MutableLiveData<Report> reportMutableLiveData = new MutableLiveData<>();

    @Inject
    public ReportViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Report> getReportMutableLiveData() {
        return reportMutableLiveData;
    }

    public void setReport(Report report) {
        reportMutableLiveData.setValue(report);
    }
}
