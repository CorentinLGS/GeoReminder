package georeminder;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.georeminder.R;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import rv.components.BasicReminderAdapter;
import rv.components.GeoReminderAdapter;
import rv.components.RoutineReminderAdapter;

public class RemindersFragment extends Fragment {

    public static RecyclerView basicRecyclerView;
    private TextView textView;
    private BasicReminderAdapter basicAdapter;
    private GeoReminderAdapter geoAdapder;
    private RoutineReminderAdapter routinesAdapter;
    private FloatingActionButton reminders_fab;

    private Intent intent;

    private int type;

    private View v;

    public RemindersFragment(){}
    public RemindersFragment(int type){
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)          {
        v = inflater.inflate(R.layout.reminders_fragment, container, false);
        initRecycler();
        return v;
    }

    private void initRecycler(){
        textView = v.findViewById(R.id.recycler_text);
        reminders_fab = v.findViewById(R.id.reminders_fab);
        basicRecyclerView = v.findViewById(R.id.basic_recycler_fragment);
        basicAdapter = new BasicReminderAdapter(MainActivity.dbmanager.getUser_().getBasic_());
        basicAdapter.addContext(getActivity());
        geoAdapder = new GeoReminderAdapter(MainActivity.dbmanager.getUser_().getGeo_());
        geoAdapder.addContext(getActivity());
        routinesAdapter = new RoutineReminderAdapter(MainActivity.dbmanager.getUser_().getRoutines());
        routinesAdapter.addContext(getActivity());
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        basicRecyclerView.setLayoutManager(layout);
        basicRecyclerView.setItemAnimator(new DefaultItemAnimator());
        basicRecyclerView.addItemDecoration(new DividerItemDecoration(basicRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        switch(type){
            case 0:
                textView.setText("Reminders");
                basicRecyclerView.setAdapter(basicAdapter);
                intent = new Intent(getContext(), AddNoteActivity.class);
                break;
            case 1:
                textView.setText("GeoReminders");
                basicRecyclerView.setAdapter(geoAdapder);
                intent = new Intent(getContext(), AddGeoActivity.class);
                break;
            case 2:
                textView.setText("Routines");
                basicRecyclerView.setAdapter(routinesAdapter);
                intent = new Intent(getContext(), AddRoutineActivity.class);
                break;
            default:
                break;
        }

        reminders_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent != null)
                    getContext().startActivity(intent);
            }
        });

    }

    public static RemindersFragment newInstance(int type) {
        return (new RemindersFragment(type));
    }

}
