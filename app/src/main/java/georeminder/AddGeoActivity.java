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

import com.example.georeminder.R;
import java.util.Calendar;

import models.GeoReminder;


public class AddGeoActivity extends Activity{

    private EditText title;
    private EditText text;
    public static EditText adress;
    private ImageButton validate;
    private ImageButton cancel;
    private LinearLayout vcView;

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


        initViews();
        initButtons();
    }

    private void initViews(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        title.setHeight(height*1/14);
        text.setHeight(height*9/14);
        adress.setHeight(height*2/14);
        vcView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height*1/14));
        validate.setLayoutParams(new LinearLayout.LayoutParams(width*1/2, height*1/14));
        cancel.setLayoutParams(new LinearLayout.LayoutParams(width*1/2, height*1/14));

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
                GeoReminder reminder = new GeoReminder(title.getText().toString(), text.getText().toString(), c.getTime(), adress.getText().toString());
                MainActivity.dbmanager.addDataToBase(reminder);
                MainActivity.dbmanager.retrieveGeoReminder();
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
