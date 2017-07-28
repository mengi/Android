package moda.menginar.mydemomapsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Menginar on 13.4.2017.
 */

public class SearchTwoActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, OnMapReadyCallback {

    private MapView mMapView;
    GoogleMap googleMap;

    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private TextView textViewName, textViewAddress, textViewPlaceId, textViewPhone, textViewWeb, textViewAtt;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_two);

        mGoogleApiClient = new GoogleApiClient.Builder(SearchTwoActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .autoCompleteTextView);
        mAutocompleteTextView.setThreshold(5);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewPlaceId = (TextView) findViewById(R.id.textViewPlaceId);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        textViewWeb = (TextView) findViewById(R.id.textViewWeb);
        textViewAtt = (TextView) findViewById(R.id.textViewAtt);

        mMapView = (MapView) findViewById(R.id.lite_mode_map);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.getMapAsync(this);
        }

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            try {
                final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
                final String placeId = String.valueOf(item.placeId);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            try {
                final Place place = places.get(0);
                CharSequence attributions = places.getAttributions();
                googleMap.clear();
                setMapLocation(googleMap, place.getLatLng());

                textViewName.setText(place.getName() + "");
                textViewAddress.setText(place.getAddress() + "");
                textViewPlaceId.setText(place.getId() + "");
                textViewPhone.setText(place.getPhoneNumber() + "");
                textViewWeb.setText(place.getWebsiteUri() + "");

                if (attributions != null) {
                    textViewAtt.setText(attributions.toString());
                }

                if (!places.getStatus().isSuccess()) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private static void setMapLocation(GoogleMap map, LatLng latLng) {

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f));
        map.addMarker(new MarkerOptions().position(latLng));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onMapReady(GoogleMap googleMaps) {
        MapsInitializer.initialize(getApplicationContext());
        googleMap = googleMaps;
    }
}