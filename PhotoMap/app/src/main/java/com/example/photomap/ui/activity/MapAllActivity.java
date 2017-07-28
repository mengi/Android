package com.example.photomap.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.photomap.R;
import com.example.photomap.connection.UserRetrofitClient;
import com.example.photomap.connection.UserService;
import com.example.photomap.model.Image;
import com.example.photomap.model.Message;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.photomap.ui.activity.LoginActivity.user;
import static com.example.photomap.util.GeneralUtil.createDrawableFromView;
import static com.example.photomap.util.GeneralUtil.isFilePath;

/**
 * Created by menginar on 20.07.2017.
 */

public class MapAllActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView imageView;
    private String imagePath = "";
    UserService userService;
    private List<Image> ımageNewList;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_all);

        view = getWindow().getDecorView().getRootView();
        imagePath = Environment.getExternalStorageDirectory().toString() + "/MapImageApp/";
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getAllImages(googleMap);
    }

    public void getAllImages(final GoogleMap googleMap) {
        try {
            ımageNewList = new ArrayList<>();
            userService = UserRetrofitClient.getApiService();
            Call<List<Image>> listCall = userService.getAllImage(user.getUserId());

            listCall.enqueue(new Callback<List<Image>>() {
                @Override
                public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                    ımageNewList = new ArrayList<Image>();
                    ımageNewList = response.body();
                    imageOnMap(googleMap);
                }

                @Override
                public void onFailure(Call<List<Image>> call, Throwable t) {
                    Snackbar.make(view, "Connection Failed With Server", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imageOnMap(GoogleMap googleMap) {
        mMap = googleMap;
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_layout, null);
        imageView = (ImageView) marker.findViewById(R.id.image);

        for (Image imageNew : ımageNewList) {
            if (isFilePath(imageNew.getImagePath())) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageNew.getImagePath());
                Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, 108, 98, false);

                imageView.setImageBitmap(smallMarker);
                LatLng mengimer = new LatLng(Double.valueOf(imageNew.getLatitude()), Double.valueOf(imageNew.getLongitude()));

                mMap.addMarker(new MarkerOptions()
                        .position(mengimer)
                        .title("Image App")
                        .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mengimer));
            } else {
                getDeleteImage(imageNew.getUserId(), imageNew.getImagePath());
            }
        }
    }

    public void getDeleteImage(String userId, String imagePath) {
        try {
            userService = UserRetrofitClient.getApiService();
            Call<Message> call = userService.getDeleteImage(userId, imagePath);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    if (response.body().getSuccess() == 1) {
                        Snackbar.make(view, "Operation Sucessed", Snackbar.LENGTH_SHORT).show();
                    }

                    if (response.body().getSuccess() == 0) {
                        Snackbar.make(view, "Operation Failed", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}