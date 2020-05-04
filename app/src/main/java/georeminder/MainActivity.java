package georeminder;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import com.example.georeminder.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import models.DataManager;
import models.User;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser fUser;
    private User user ;
    public static DataManager dbmanager;

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private Menu menu;

    private RemindersFragment remindersFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        user = new User(fUser.getUid(), fUser.getDisplayName());

        navigationView = findViewById(R.id.main_navigation_view);

        dbmanager = new DataManager(user);
        dbmanager.retrieveAllReminders();

        initMenuButtons();
        configureToolBar();
        configureDrawerLayout();

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }

        showRemindersFragment();
    }

    private void configureToolBar(){
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout(){
        drawerLayout = findViewById(R.id.main_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void showRemindersFragment(){
        if (remindersFragment == null) remindersFragment = RemindersFragment.newInstance(0);
        else{
            remindersFragment = null;
            remindersFragment = RemindersFragment.newInstance(0);
        }
        startTransactionFragment(remindersFragment);
    }

    private void showGeoFragment(){
        if (remindersFragment == null) remindersFragment = RemindersFragment.newInstance(1);
        else{
            remindersFragment = null;
            remindersFragment = RemindersFragment.newInstance(1);
        }
        startTransactionFragment(remindersFragment);
    }

    private void showRoutineFragment(){
        if (remindersFragment == null) remindersFragment = RemindersFragment.newInstance(2);
        else{
            remindersFragment = null;
            remindersFragment = RemindersFragment.newInstance(2);
        }
        startTransactionFragment(remindersFragment);
    }

    private void startTransactionFragment(RemindersFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_framelayout, fragment).commit();
    }

    private void initMenuButtons(){
        menu = navigationView.getMenu();
        menu.findItem(R.id.reminders_menu_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showRemindersFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        menu.findItem(R.id.geo_menu_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showGeoFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        menu.findItem(R.id.routines_menu_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showRoutineFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        menu.findItem(R.id.logout_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }
}
