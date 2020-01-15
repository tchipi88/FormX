package com.appli.nyx.formx.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.preference.PrefsManager;
import com.appli.nyx.formx.preference.PrefsManager_;
import com.appli.nyx.formx.ui.viewmodel.NetworkErrorViewModel;
import com.appli.nyx.formx.utils.NetworkUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector, BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
    @Inject
    protected DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;

    protected BottomNavigationView bottomNavigationView;
    protected FirebaseFirestore mFirestore;
    protected Toolbar toolbar;
    protected NavController navController;
    protected Snackbar snackbar;
    protected boolean internetConnected = true;

    protected PrefsManager prefsManager;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            NetworkErrorViewModel networkErrorViewModel = ViewModelProviders.of(MainActivity.this, viewModelFactory).get(NetworkErrorViewModel.class);
            String internetStatus;
            if (NetworkUtils.isConnected(context)) {
                internetStatus = getString(R.string.internet_connected);
                networkErrorViewModel.connected();
            } else {
                internetStatus = getString(R.string.lost_internet_connection);
                networkErrorViewModel.unconnected();
            }
            snackbar = Snackbar.make(bottomNavigationView, internetStatus, Snackbar.LENGTH_LONG);

            if (internetStatus.equalsIgnoreCase(getString(R.string.lost_internet_connection))) {
                if (internetConnected) {
                    snackbar.show();
                    internetConnected = false;
                }
            } else {
                if (!internetConnected) {
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    internetConnected = true;
                    snackbar.show();
                }
            }
        }
    };

    public void setBottomNavigationVisibility(boolean visible) {
        if (bottomNavigationView.isShown() && !visible) {
            bottomNavigationView.setVisibility(View.GONE);
        } else if (!bottomNavigationView.isShown() && visible) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    /**
     * Method to register runtime broadcast receiver to show snackbar alert for internet connection..
     */
    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentAndroidInjector;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_main);

        prefsManager = PrefsManager_.getInstance_(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        navController.setGraph(R.navigation.nav_graph);
        NavigationUI.setupActionBarWithNavController(this, navController);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            toolbar.setLogo(null);
            toolbar.setVisibility(View.VISIBLE);
            setBottomNavigationVisibility(true);

            switch (destination.getId()) {

                case R.id.termsFragment:
                case R.id.privacyTermsFragment:
                case R.id.privacyFragment: {
                    setBottomNavigationVisibility(false);
                    break;
                }
                case R.id.signInFragment:
                case R.id.signUpFragment:
                case R.id.resetPasswordRequestFragment:
                case R.id.onboardingFragment: {
                    bottomNavigationView.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    break;
                }


            }
        });

        mFirestore = FirebaseFirestore.getInstance();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_cluster:
                navController.navigate(R.id.action_global_clusterListFragment);
                break;
            case R.id.navigation_enquetes:
                navController.navigate(R.id.action_global_enqueteMenuFragment);
                break;
            case R.id.navigation_form:
                navController.navigate(R.id.action_global_formListFragment);
                break;
            case R.id.navigation_report:
                navController.navigate(R.id.action_global_reportsListFragment);
                break;

            case R.id.navigation_settings:
                navController.navigate(R.id.action_global_settingsFragment);
                break;
        }
        return true;
    }
}
