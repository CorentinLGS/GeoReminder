package georeminder;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.Toast;

import com.example.georeminder.R;

import java.util.ArrayList;
import java.util.Calendar;

import models.GeoReminder;


public class AddGeoActivity extends Activity{

    private EditText title;
    private EditText text;
    public static EditText adress;
    private ImageButton validate;
    private ImageButton cancel;
    private LinearLayout vcView;
    private String uri = null;

    private GoogleMapsFragment mapsFragment;


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
        if(getIntent().getExtras()!=null) {
            Bundle data = getIntent().getExtras();
            title.setText(data.getString("title"));
            text.setText(data.getString("text"));
            adress.setText(data.getString("gps"));
            uri = data.getString("uri");
        }


        initViews();
        initButtons();
    }

    private void initViews(){

       mapsFragment = GoogleMapsFragment.newInstance();

        adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fmanager = getFragmentManager();
                FragmentTransaction transaction = fmanager.beginTransaction();
                //transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);
                transaction.replace(R.id.geo_constaint, mapsFragment, String.valueOf(R.layout.fragment_google_maps));
                transaction.addToBackStack(String.valueOf(R.layout.fragment_google_maps));
                transaction.commit();

            }
        });
    }


    private void initButtons(){
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                if( title.getText().length() != 0 && adress.getText().length() != 0) {
                    String test = title.getText().toString();
                    GeoReminder reminder = new GeoReminder(title.getText().toString(), text.getText().toString(), c.getTime().getTime(), adress.getText().toString());
                    reminder.setUri(uri);
                    MainActivity.dbmanager.addDataToBase(reminder);
                    MainActivity.dbmanager.retrieveGeoReminder();
                    finish();
                }
                else{
                    Toast toast = Toast.makeText(getBaseContext(), "Please fill in all the information", Toast.LENGTH_SHORT);
                    toast.show();
                }
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
