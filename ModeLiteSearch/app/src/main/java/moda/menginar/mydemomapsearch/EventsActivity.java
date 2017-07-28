package moda.menginar.mydemomapsearch;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by Menginar on 10.4.2017.
 */

public class EventsActivity extends AppCompatActivity
        implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,
        OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapClick(LatLng point) {
        String address = getAddress(point);
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    @Override
    public void onMapLongClick(LatLng point) {
        String address = getAddress(point);
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }


    public String getAddress(LatLng point) {
        List<Address> addressList;
        StringBuilder builder = new StringBuilder();
        try {

            Geocoder geocoder;
            geocoder = new Geocoder(this);

            if (!point.toString().isEmpty()) {
                addressList = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                builder.append(addressList.get(0).getAddressLine(0));
                builder.append(" - ");
                builder.append(addressList.get(0).getAddressLine(1));
                builder.append(" - ");
                builder.append(addressList.get(0).getAddressLine(2));

                return builder.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

}
