package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.appli.nyx.formx.model.firebase.Cluster;

import javax.inject.Inject;

public class ClusterViewModel extends AndroidViewModel {

    private MutableLiveData<Cluster> clusterMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> clusterCollectionPathMutableLiveData = new MutableLiveData<>();

    @Inject
    public ClusterViewModel(@NonNull Application application) {
        super(application);
    }

	public MutableLiveData<Cluster> getClusterMutableLiveData() {
		return clusterMutableLiveData;
    }

    public void setCluster(Cluster cluster) {
        clusterMutableLiveData.setValue(cluster);
    }

    public MutableLiveData<String> getClusterCollectionPathMutableLiveData() {
        return clusterCollectionPathMutableLiveData;
    }

    public void setClusterCollectionPathMutableLiveData(String clusterPath) {
        this.clusterCollectionPathMutableLiveData.setValue(clusterPath);
    }

}
