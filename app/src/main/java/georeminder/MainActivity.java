package georeminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.georeminder.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import models.DataManager;
import models.User;
import rv.components.BasicReminderAdapter;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser fUser;
    private User user ;
    public static DataManager dbmanager;

    public static RecyclerView basicRecyclerView;
    private BasicReminderAdapter basicAdapter;
    private FloatingActionButton fab_addreminder;
    private FloatingActionButton fab_breminder;
    private FloatingActionButton fab_routine;
    private FloatingActionButton fab_geo;
    private LinearLayout fab_linear;
    private boolean clicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        user = new User(fUser.getUid(), fUser.getDisplayName());
        fab_breminder= findViewById(R.id.basic_reminder_button);
        fab_routine= findViewById(R.id.routine_button);
        fab_geo= findViewById(R.id.geo_reminder_button);
        fab_linear = findViewById(R.id.linear_fab);
        fab_addreminder = findViewById(R.id.addreminder_floatingbutton);

        fab_addreminder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!clicked) {
                    fab_linear.setVisibility(View.VISIBLE);
                    clicked = true;
                }
                else {
                    fab_linear.setVisibility(View.GONE);
                    clicked = false;
                }
            }
        });

        dbmanager = new DataManager(user);
        dbmanager.retrieveAllReminders();

        initRecycler();
        initFABs();

        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }

    }

    private void initRecycler(){
        basicRecyclerView = findViewById(R.id.basic_recycler);
        basicAdapter = new BasicReminderAdapter(user.getBasic_());
        LinearLayoutManager layout = new LinearLayoutManager(this);
        basicRecyclerView.setLayoutManager(layout);
        basicRecyclerView.setItemAnimator(new DefaultItemAnimator());
        basicRecyclerView.addItemDecoration(new DividerItemDecoration(basicRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        basicRecyclerView.setAdapter(basicAdapter);
    }

    private void initFABs(){
        fab_breminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddNoteActivity.class);
                fab_linear.setVisibility(View.GONE);
                clicked = false;
                startActivity(intent);
            }
        });

        fab_routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddRoutineActivity.class);
                fab_linear.setVisibility(View.GONE);
                clicked = false;
                startActivity(intent);
            }
        });

        fab_geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddGeoActivity.class);
                fab_linear.setVisibility(View.GONE);
                clicked = false;
                startActivity(intent);
            }
        });
    }
}
