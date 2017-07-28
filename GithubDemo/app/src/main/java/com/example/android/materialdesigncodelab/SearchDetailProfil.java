package com.example.android.materialdesigncodelab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import apiservice.GithubService;
import apiservice.RetroClient;
import model.User;
import model.UserSearch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Menginar on 27.3.2017.
 */

public class SearchDetailProfil extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_TYPE = "state";
    public String username = "";

    TextView textName, textEmail, textFollowers, textFollowing;
    ImageView imgProfile;

    // GithubApiService
    GithubService githubApi;

    // ProgressDialog
    ProgressDialog progressDialog;

    // User
    User user;

    // CollapsingToolbarLayout
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        textName = (TextView) findViewById(R.id.text_name_detail);
        textEmail = (TextView) findViewById(R.id.text_email_detail);
        textFollowers = (TextView) findViewById(R.id.text_followers_detail);
        textFollowing = (TextView) findViewById(R.id.text_following_detail);
        imgProfile = (ImageView) findViewById(R.id.image_profile_user);

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        username = SearchProfil.userSearch.getItems().get(postion).getLogin();
        getUserProfile(username);

    }

    public void getUserProfile(String username) {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait.. ...");
            progressDialog.show();
            getUser(username);
        } catch (Exception e) {

        }
    }

    public void getUser(String username) {
        try {
            githubApi = RetroClient.getGithubService();

            Call<User> calluser = githubApi.getUser(username);
            calluser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = new User();
                    user = response.body();
                    try {
                        collapsingToolbar.setTitle(user.getLogin());
                        textName.setText(user.getName());
                        textEmail.setText(user.getEmail());
                        textFollowing.setText("Following : " + Integer.toString(user.getFollowing()));
                        textFollowers.setText("Followers : " + Integer.toString(user.getFollowers()));

                        Picasso.with(getApplicationContext())
                                .load(user.getAvatarUrl())
                                .into(imgProfile);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Snackbar.make(getCurrentFocus(), "No Connection to server", Snackbar.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
