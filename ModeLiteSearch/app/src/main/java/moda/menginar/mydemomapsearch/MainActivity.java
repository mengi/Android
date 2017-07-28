package moda.menginar.mydemomapsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView liteModeText;
    GoogleMap googleMap;
    MapView mMapView;

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    public LatLng searchAdress;

    PlaceAutocompleteFragment places;
    AutocompleteFilter typeFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        liteModeText = (TextView) findViewById(R.id.lite_mode_text);
        mMapView = (MapView) findViewById(R.id.lite_mode_map);

        places = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        typeFilter = new AutocompleteFilter.Builder().build();

        places.setFilter(typeFilter);

        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                setMapLocation(googleMap, place.getLatLng());
            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();

            }
        });


        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMaps) {
        MapsInitializer.initialize(getApplicationContext());
        googleMap = googleMaps;
        setMapLocation(googleMap, HAMBURG);
    }

    private static void setMapLocation(GoogleMap map, LatLng latLng) {

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f));
        map.addMarker(new MarkerOptions().position(latLng));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void searchTwo(View view) {
        try {
            startActivity(new Intent(MainActivity.this, SearchTwoActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void eventMap(View view) {
        try {
            startActivity(new Intent(MainActivity.this, EventsActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void liteModeMap(View view) {
        try {
            startActivity(new Intent(MainActivity.this, ListeMode.class));
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    // Search
    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, 2);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);
                searchAdress = place.getLatLng();
                googleMap.clear();
                setMapLocation(googleMap, searchAdress);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }
}

