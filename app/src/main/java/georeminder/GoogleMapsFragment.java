package georeminder;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import android.app.Fragment;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.georeminder.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {

    private MapView maps;
    private GoogleMap gmap;
    private ImageView dropPinView;
    private Button dropMarkerButton;
    private LocationManager locManager;
    private Geocoder geocoder;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static GoogleMapsFragment newInstance() {
        return new GoogleMapsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)          {
        View v = inflater.inflate(R.layout.fragment_google_maps, container, false);

        maps = (MapView) v.findViewById(R.id.mapView);
        maps.onCreate(savedInstanceState);

        maps.getMapAsync(this);

        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        geocoder = new Geocoder(getActivity());

        dropPinView = new ImageView(v.getContext());
        dropPinView.setImageResource(R.drawable.ic_droppin);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        float density = getResources().getDisplayMetrics().density;
        params.bottomMargin = (int) (12 * density);
        dropPinView.setLayoutParams(params);
        maps.addView(dropPinView);

        dropMarkerButton = new Button(v.getContext());
        ViewGroup.LayoutParams buttonParams =  new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        dropMarkerButton.setLayoutParams(buttonParams);
        dropMarkerButton.setText("Add Marker");

        dropMarkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarkerOptions marker = new MarkerOptions()
                        .position(gmap.getCameraPosition().target)
                        .title("Chosen position");
                gmap.addMarker(marker);
                List<Address> adress = null;
                try {
                    adress = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String adressString = "";
                if(adress != null) {
                    for (int i = 0; i < adress.size(); i++){
                        adressString = adress.get(i).getAddressLine(i);
                    }
                }
                AddGeoActivity.adress.setText(adressString);
                getActivity().onBackPressed();

            }
        });

        maps.addView(dropMarkerButton);
        return v;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        maps.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        maps.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        maps.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        maps.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        }
        else{
            gmap = map;
            gmap.getUiSettings().setMyLocationButtonEnabled(false);
            gmap.setMyLocationEnabled(true);

            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            MapsInitializer.initialize(this.getActivity());

            List<String> providers = locManager.getProviders(true);
            double latitude = 0,longitude=0;
            Location l = null;
            for (int i = 0; i < providers.size(); i++) {
                l = locManager.getLastKnownLocation(providers.get(i));
                if (l != null)
                    break;
            }
            if (l != null) {
                latitude = l.getLatitude();
                longitude = l.getLongitude();
            }

            // Updates the location and zoom of the MapView
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10);
            gmap.animateCamera(cameraUpdate);
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onPause() {
        maps.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        maps.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        maps.onLowMemory();
    }


}
