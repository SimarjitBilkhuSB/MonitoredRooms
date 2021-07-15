package com.example.monitoredrooms;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.monitoredrooms.databinding.ActivityMonitoredRoomsBinding;
import com.example.monitoredrooms.utility.AuthenticationHelper;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MonitoredRoomsActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMonitoredRoomsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMonitoredRoomsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMonitoredRooms.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // When creating new features, add them to the app bar configuration
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_feature1, R.id.nav_feature2, R.id.nav_feature3)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_monitored_rooms);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //If the features are not fragments or activities define their actions here
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                int ID = item.getItemId();
                if (ID == R.id.logoutButton) {
                    AuthenticationHelper AuthHelper = new AuthenticationHelper(MonitoredRoomsActivity.this);
                    AuthHelper.logout();
                    goToLoginActivity();

                }
                return true;
            }
        });
    }

    //use if menu needed in activity
    /**@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.sort_rooms, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_monitored_rooms);
        //Shows a menu icon on top destination and an arrow icon for other destinations
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void goToLoginActivity(){
        Intent loginIntent = new Intent(MonitoredRoomsActivity.this, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //activities on top will be closed
        startActivity(loginIntent);
    }
}