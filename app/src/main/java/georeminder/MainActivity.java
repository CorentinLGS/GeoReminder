package georeminder;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.georeminder.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.Inet4Address;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import models.DailyReminder;
import models.DataManager;
import models.GeoReminder;
import models.MonthlyReminder;
import models.Reminder;
import models.User;
import models.WeeklyReminder;
import rv.components.RoutineReminderAdapter;
import services.GeoService;
import services.RemindersService;
import services.RoutineService;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser fUser;
    private User user;
    private GoogleSignInAccount gUser;
    public static DataManager dbmanager;

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private Menu menu;
    private FloatingActionButton fab;
    private int state;

    public Reminder currentReminder = null;

    private RemindersFragment remindersFragment;
    private static DisplayFragment displayFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        gUser = GoogleSignIn.getLastSignedInAccount(getBaseContext());
        user = new User(fUser.getUid(), fUser.getDisplayName());

        navigationView = findViewById(R.id.main_navigation_view);

        dbmanager = new DataManager(user, this);
        dbmanager.retrieveAllReminders();


        initMenuButtons();
        configureToolBar();
        configureDrawerLayout();
        initFab();
        initMenu();

        if (ActivityCompat.checkSelfPermission(this,
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

    private void configureToolBar() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout() {
        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void showRemindersFragment() {
        if (remindersFragment == null) remindersFragment = RemindersFragment.newInstance(0);
        else {
            remindersFragment = null;
            remindersFragment = RemindersFragment.newInstance(0);
        }
        state = 0;
        startTransactionFragment(remindersFragment);
    }

    private void showGeoFragment() {
        if (remindersFragment == null) remindersFragment = RemindersFragment.newInstance(1);
        else {
            remindersFragment = null;
            remindersFragment = RemindersFragment.newInstance(1);
        }
        state = 1;
        startTransactionFragment(remindersFragment);
    }

    private void showRoutineFragment() {
        if (remindersFragment == null) remindersFragment = RemindersFragment.newInstance(2);
        else {
            remindersFragment = null;
            remindersFragment = RemindersFragment.newInstance(2);
        }
        state = 2;
        startTransactionFragment(remindersFragment);
    }

    private void startTransactionFragment(RemindersFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_framelayout, fragment).commit();
    }

    private void initMenuButtons() {
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

    private void initFab() {
        fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (state) {
                    case 0:
                        intent = new Intent(getBaseContext(), AddNoteActivity.class);
                        break;
                    case 1:
                        intent = new Intent(getBaseContext(), AddGeoActivity.class);
                        break;
                    case 2:
                        intent = new Intent(getBaseContext(), AddRoutineActivity.class);
                        break;
                    default:
                        break;
                }
                if (intent != null) {
                    if (currentReminder != null) {
                        Bundle bundle = currentReminder.takeData();
                        intent.putExtras(bundle);
                    }
                    startActivity(intent);
                    currentReminder = null;
                }
            }
        });
    }

    private void initMenu() {
        View header = navigationView.getHeaderView(0);
        TextView email = header.findViewById(R.id.header_email);
        email.setText(gUser.getEmail());
        TextView username = header.findViewById(R.id.header_name);
        username.setText(gUser.getDisplayName());
        ImageView userImage = header.findViewById(R.id.header_image);
        Glide.with(this).load(gUser.getPhotoUrl()).into(userImage);
    }

    private void StartTransactionDisplayFragment(DisplayFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_framelayout, fragment).commit();
    }


    public void showDisplayFragment(Reminder reminder) {
        if (displayFragment == null) displayFragment = DisplayFragment.newInstance(reminder);
        else {
            displayFragment = null;
            displayFragment = DisplayFragment.newInstance(reminder);
        }
        StartTransactionDisplayFragment(displayFragment);
        currentReminder = reminder;
    }
}
