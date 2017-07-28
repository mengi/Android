package com.example.photomap.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.photomap.R;
import com.example.photomap.connection.UserRetrofitClient;
import com.example.photomap.connection.UserService;
import com.example.photomap.model.Image;
import com.example.photomap.model.Message;
import com.example.photomap.ui.activity.DetailActivity;
import com.example.photomap.util.GridViewAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.photomap.ui.activity.LoginActivity.user;
import static com.example.photomap.ui.activity.MainActivity.latitudeNetwork;
import static com.example.photomap.ui.activity.MainActivity.longitudeNetwork;

/**
 * Created by ss on 22.7.2017.
 */

public class HomepageFragment extends Fragment {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private List<String> imageItems;
    private String imagePath = "";
    private String imagePathTwo = "";
    File savedFileDestination;
    private static final int PICK_CAMERA_IMAGE = 2;
    UserService userService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        imagePath = Environment.getExternalStorageDirectory().toString() + "/MapImageApp/";
        imageItems = new ArrayList<>();
        setHasOptionsMenu(true);

        getImages();

        gridView = (GridView) view.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this.getActivity(), R.layout.grid_item, imageItems);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String imagePath = ((TextView) v.findViewById(R.id.text)).getText().toString();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("imagePath", imagePath);

                startActivity(intent);
            }
        });
        gridView.setAdapter(gridAdapter);
        return view;
    }

    private List<String> getImages() {
        new File(imagePath).mkdirs();
        File fileTarget = new File(imagePath);
        File[] files = fileTarget.listFiles();
        imageItems.clear();
        if (files != null) {
            for (File file : files) {
                imageItems.add(file.getAbsolutePath());
            }
        }
        return imageItems;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            Image ımageNew = new Image();
            ımageNew.setUserId(user.getUserId());
            ımageNew.setImagePath(imagePathTwo);
            ımageNew.setLatitude(latitudeNetwork);
            ımageNew.setLongitude(longitudeNetwork);
            getInsertImage(ımageNew);
        }
        getImages();
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        getImages();
        gridAdapter.notifyDataSetChanged();
        super.onResume();
    }

    public void getInsertImage (Image ımageNew) {
        userService = UserRetrofitClient.getApiService();
        try {
            Call<Message> call = userService.getInsertImage(ımageNew.getUserId(), ımageNew.getImagePath(), ımageNew.getLatitude(), ımageNew.getLongitude());
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    if (response.body().getSuccess() == 1) {
                        Snackbar.make(getView(), "Operation Sucessed", Snackbar.LENGTH_SHORT).show();
                    }

                    if (response.body().getSuccess() == 0) {
                        Snackbar.make(getView(), "Operation Failed", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    Snackbar.make(getView(), "Operation Failed", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_camera, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera:
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                String name = dateFormat.format(new Date());
                savedFileDestination = new File(imagePath, name + ".jpg");

                imagePathTwo = savedFileDestination.toString();

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(savedFileDestination));
                startActivityForResult(intent, PICK_CAMERA_IMAGE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
