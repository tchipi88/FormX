package com.appli.nyx.formx.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.appli.nyx.formx.ui.viewmodel.EnqueteViewModel;
import com.appli.nyx.formx.ui.viewmodel.FormViewModel;
import com.appli.nyx.formx.ui.viewmodel.NetworkErrorViewModel;
import com.appli.nyx.formx.ui.viewmodel.SignInViewModel;
import com.appli.nyx.formx.ui.viewmodel.UserViewModel;
import com.appli.nyx.formx.ui.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsSignInViewModel(SignInViewModel signInViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsUserViewModel(UserViewModel UserViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NetworkErrorViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsNetworkErrorViewModel(NetworkErrorViewModel networkErrorViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FormViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsFormViewModel(FormViewModel formViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EnqueteViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsEnqueteViewModel(EnqueteViewModel enqueteViewModel);

    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
