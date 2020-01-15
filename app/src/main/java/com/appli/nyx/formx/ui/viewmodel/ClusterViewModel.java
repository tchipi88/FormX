package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Cluster;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class ClusterViewModel extends AndroidViewModel {

    private MutableLiveData<Cluster> clusterMutableLiveData = new MutableLiveData<>();

    @Inject
    public ClusterViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Cluster>> loadClusterByUser() {
        List<Cluster> clusters = Arrays.asList(new Cluster("Cluster  1"),
                new Cluster("Cluster  2"),
                new Cluster("Cluster  3"),
                new Cluster("Cluster  4"),
                new Cluster("Cluster  5"),
                new Cluster("Cluster  6"));
        return new MutableLiveData<>(clusters);
    }

    public void setCluster(Cluster cluster) {
        clusterMutableLiveData.setValue(cluster);
    }
}
