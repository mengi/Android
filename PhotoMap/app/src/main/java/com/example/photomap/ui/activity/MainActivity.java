package com.example.photomap.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photomap.R;
import com.example.photomap.connection.UserRetrofitClient;
import com.example.photomap.connection.UserService;
import com.example.photomap.model.User;
import com.example.photomap.ui.fragment.HomepageFragment;
import com.example.photomap.ui.fragment.ProfileFragment;
import com.example.photomap.ui.fragment.ComplainFragment;
import com.example.photomap.util.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.photomap.util.GeneralUtil.StringToBitmap;
import static com.example.photomap.ui.activity.LoginActivity.user;
import static com.example.photomap.util.GeneralUtil.transform;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_ID;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_URL;

/**
 * Created by ss on 20.7.2017.
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView ımageView;
    TextView txtFullName, txtEmail;
    UserService userService;

    private LocationManager locationManager;
    public static String latitudeNetwork;
    public static String longitudeNetwork;

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = getWindow().getDecorView().getRootView();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (isLocationEnabled()) {
            try {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 12000, 0, locationListenerGPS);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

            try {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        if (user != null) {
            getUserData(user.getUserId());
        } else {
            getUserData(SharedPreferencesUtil.getFromSharedPrefs(MainActivity.this, PRE_ID));
        }

        try {
            View header = navigationView.getHeaderView(0);
            txtFullName = (TextView) header.findViewById(R.id.txt_name);
            txtEmail = (TextView) header.findViewById(R.id.txt_email);
            ımageView = (ImageView) header.findViewById(R.id.profile_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupToolbar();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();

                        if (R.id.item_navigation_drawer_homepage == id) {
                            HomePage();
                        } else if (R.id.item_navigation_drawer_profile == id) {
                            Profile();
                        } else if (R.id.item_navigation_drawer_settings == id) {
                            Settings();
                        } else if (R.id.item_navigation_drawer_signout == id) {
                            logout();
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
        HomePage();
    }

    public void getUserData(final String userId) {
        try {
            userService = UserRetrofitClient.getApiService();
            Call<User> userCall = userService.getUserData(userId);

            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = new User();
                    user = response.body();
                    txtEmail.setText(user.getUserEmail());
                    txtFullName.setText(user.getUserName());
                    ımageView.setImageBitmap(transform(StringToBitmap(SharedPreferencesUtil.getFromSharedPrefs(MainActivity.this, PRE_URL))));
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Snackbar.make(view, "Connection Failed With Server", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_map:
                startActivity(new Intent(getApplication(), MapAllActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    protected void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra(LoginActivity.LOGOUT, LoginActivity.LOGOUT);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setMessage("Are you sure EXIT?");
        alertdialog.setCancelable(false).setPositiveButton("Evet", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).setNegativeButton("Hayır", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = alertdialog.create();
        alert.show();
    }

    public void HomePage() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new HomepageFragment()).commit();
    }

    public void Settings() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ComplainFragment()).commit();
    }

    public void Profile() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = String.valueOf(location.getLongitude());
            latitudeNetwork = String.valueOf(location.getLatitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private final LocationListener locationListenerGPS = new LocationListener() {

        public void onLocationChanged(Location location) {
            longitudeNetwork = String.valueOf(location.getLongitude());
            latitudeNetwork = String.valueOf(location.getLatitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}
