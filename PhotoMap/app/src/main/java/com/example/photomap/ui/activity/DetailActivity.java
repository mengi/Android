package com.example.photomap.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.photomap.R;
import com.example.photomap.connection.UserRetrofitClient;
import com.example.photomap.connection.UserService;
import com.example.photomap.model.Image;
import com.example.photomap.model.Message;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.photomap.ui.activity.LoginActivity.user;
import static com.example.photomap.util.GeneralUtil.createDrawableFromView;
import static com.example.photomap.util.GeneralUtil.getLocation;


public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String imagePath = "";
    UserService userService;
    ImageView imageCard, imageActionLike, imageActionDelete, imageActionShare;
    View view;
    private Image ımage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        view = getWindow().getDecorView().getRootView();
        imagePath = "file://" + getIntent().getStringExtra("imagePath");
        initView();



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Glide.with(DetailActivity.this)
                .load(imagePath)
                .fitCenter()
                .centerCrop()
                .into(imageCard);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getImageInfo(user.getUserId(), getIntent().getStringExtra("imagePath"));
    }

    public void initView() {
        imageCard = (ImageView) findViewById(R.id.imageCard);
        imageActionLike = (ImageView) findViewById(R.id.imageActionLike);
        imageActionDelete = (ImageView) findViewById(R.id.imageActionDelete);
        imageActionShare = (ImageView) findViewById(R.id.imageActionShare);

        imageActionLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Like Added", Snackbar.LENGTH_SHORT).show();
            }
        });

        imageActionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeleteImage(user.getUserId(), getIntent().getStringExtra("imagePath"));
            }
        });
        imageActionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });
    }

    public boolean deleteImage() {
        boolean statu = false;
        try {
            File file = new File(getIntent().getStringExtra("imagePath"));
            if (file.exists()) {
                file.delete();
            }
            statu = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statu;
    }

    public void getDeleteImage(String userId, String imagePath) {
        try {
            userService = UserRetrofitClient.getApiService();
            Call<Message> call = userService.getDeleteImage(userId, imagePath);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    if (response.body().getSuccess() == 1) {
                        if (deleteImage()) {
                            finish();
                        } else {
                            Snackbar.make(view, "Operation Failed", Snackbar.LENGTH_SHORT).show();
                        }
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

    private void shareIt() {
        File imageFile = new File(getIntent().getStringExtra("imagePath"));

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/jpg");

        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Image App");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));

        startActivity(Intent.createChooser(sharingIntent, "Image App"));
    }

    public void imageOnMap() {
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_layout, null);
        ImageView imageView = (ImageView) marker.findViewById(R.id.image);
        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra("imagePath"));

        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, 108, 98, false);
        imageView.setImageBitmap(smallMarker);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);

        LatLng sydney = new LatLng(Double.valueOf(ımage.getLatitude()), Double.valueOf(ımage.getLongitude()));
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Image App")
                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this, marker))));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(zoom);
    }

    public void getImageInfo (String userId, String imagePath) {
        userService = UserRetrofitClient.getApiService();

        try {
            Call<Image> call = userService.getImageInfo(userId, imagePath);
            call.enqueue(new Callback<Image>() {
                @Override
                public void onResponse(Call<Image> call, Response<Image> response) {
                    ımage = new Image();
                    ımage = response.body();
                    imageOnMap();
                }

                @Override
                public void onFailure(Call<Image> call, Throwable t) {
                    Snackbar.make(view, "Operation Failed", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

