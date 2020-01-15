package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Enquete;
import com.appli.nyx.formx.model.firebase.Report;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class ReportViewModel extends AndroidViewModel {

    private MutableLiveData<Report> reportMutableLiveData = new MutableLiveData<>();

    @Inject
    public ReportViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Report>> loadReportByUser() {
        List<Report> reports = Arrays.asList(new Report("Report  1"),
                new Report("Report  2"),
                new Report("Report  3"),
                new Report("Report  4"),
                new Report("Report  5"),
                new Report("Report  6"));
        return new MutableLiveData<>(reports);
    }

    public void setReport(Report report) {
        reportMutableLiveData.setValue(report);
    }
}
