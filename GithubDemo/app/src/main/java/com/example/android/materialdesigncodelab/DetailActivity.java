/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.materialdesigncodelab;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import apiservice.GithubService;
import apiservice.RetroClient;
import model.Followers;
import model.Following;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        textName = (TextView) findViewById(R.id.text_name_detail);
        textEmail = (TextView) findViewById(R.id.text_email_detail);
        textFollowers = (TextView) findViewById(R.id.text_followers_detail);
        textFollowing = (TextView) findViewById(R.id.text_following_detail);
        imgProfile = (ImageView) findViewById(R.id.image_profile_user);

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        String type = getIntent().getStringExtra(EXTRA_TYPE);

        try {
            if (type.equals("Following")) {
                username = SearchActivity.followingsList.get(postion).getLogin();
                progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please Wait.. ...");
                progressDialog.show();
                getUser(username);
            }

            if (type.equals("Followers")) {
                username = SearchActivity.followersList.get(postion).getLogin();
                progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please Wait.. ...");
                progressDialog.show();
                getUser(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
