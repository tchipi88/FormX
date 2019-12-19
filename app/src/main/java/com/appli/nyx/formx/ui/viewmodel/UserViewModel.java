package com.appli.nyx.formx.ui.viewmodel;

import android.app.Application;

import com.appli.nyx.formx.model.firebase.User;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {

	private MutableLiveData<User> userLiveData = new MutableLiveData<>();

	@Inject
	public UserViewModel(@NonNull Application application) {
		super(application);
	}

	public LiveData<User> getObservableUser() {
		return userLiveData;
	}

	public void setUser(User user) {
		this.userLiveData.postValue(user);
	}
}
