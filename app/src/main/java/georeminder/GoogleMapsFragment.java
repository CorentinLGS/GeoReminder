package georeminder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.georeminder.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {

    private MapView maps;
    private GoogleMap map;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)          {
        View v = inflater.inflate(R.layout.fragment_google_maps, container, false);

        // Gets the MapView from the XML layout and creates it
        maps = (MapView) v.findViewById(R.id.mapView);
        maps.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = maps.getMapAsync();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //ImageView dropPinView = new ImageView(this);
        //dropPinView.setImageResource(R.drawable.ic_droppin_24dp);

        //FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        //float density = getResources().getDisplayMetrics().density;
        //params.bottomMargin = (int) (12 * density);
        //dropPinView.setLayoutParams(params);
    }


    @Override
    public void onResume() {
        maps.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        maps.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        maps.onLowMemory();
    }


}
