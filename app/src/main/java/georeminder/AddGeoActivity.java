package georeminder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.georeminder.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import models.GeoReminder;
import models.Reminder;

public class AddGeoActivity extends Activity{

    private EditText title;
    private EditText text;
    private EditText adress;
    private Calendar calendar;
    private ImageButton validate;
    private ImageButton cancel;
    private LinearLayout vcView;

    private FragmentManager fm;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgeo);

        title = findViewById(R.id.geo_title);
        text = findViewById(R.id.geo_content);
        adress = findViewById(R.id.geo_adress);
        validate = findViewById(R.id.validate_button_geo);
        cancel = findViewById(R.id.cancel_button_geo);
        vcView = findViewById(R.id.vc_linear_geo);

        fm = this.getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();


        initViews();
    }

    private void initViews(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        title.setHeight(height*1/14);
        text.setHeight(height*10/14);
        adress.setHeight(height*1/14);
        vcView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height*1/14));
        validate.setLayoutParams(new LinearLayout.LayoutParams(width*1/2, height*1/14));
        cancel.setLayoutParams(new LinearLayout.LayoutParams(width*1/2, height*1/14));

        adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleMapsFragment fragment = new GoogleMapsFragment();
                fragmentTransaction.add(com.google.android.gms.maps.MapFragment, fragment);
                fragmentTransaction.commit();

            }
        });
    }


    private void initButtons(){
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
