package com.example.android.materialdesigncodelab;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import apiservice.GithubService;
import apiservice.RetroClient;
import butterknife.ButterKnife;
import butterknife.Bind;
import model.Followers;
import model.Following;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menginar on 26.3.2017.
 */



public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private static final int REQUEST_SIGNUP = 0;

    // GithubApiService
    GithubService githubApi;

    // User
    public static User user;
    public static ArrayList<Following> followingsList;
    public static ArrayList<Followers> followersList;

    // ProgressDialog
    ProgressDialog progressDialog;

    @Bind(R.id.input_name) EditText userNameText;
    @Bind(R.id.btn_search) Button searchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.bind(this);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {
                    login();
                } else {
                    Snackbar.make(getCurrentFocus(), "Sorry no network connection", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void login() {
        if (!validate()) {
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait.. ...");
        progressDialog.show();

        String username = userNameText.getText().toString();
        getUser(username);

    }

    public boolean validate() {
        boolean valid = true;

        String userName= userNameText.getText().toString();

        if (userName.isEmpty()) {
            userNameText.setError("Fill in the required fields");
            valid = false;
        } else {
            userNameText.setError(null);
        }

        return valid;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void getUser(String username) {
        try {
            githubApi = RetroClient.getGithubService();

            Call<User> calluser = githubApi.getUser(username);
            calluser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    try {
                        user = response.body();
                        getUserFollowing(user.getLogin());
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Snackbar.make(getCurrentFocus(), "No Users", Snackbar.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    progressDialog.dismiss();
                    Snackbar.make(getCurrentFocus(), "No Connection to server", Snackbar.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getUserFollowing(String Username) {
        try {
            String username = SearchActivity.user.getLogin();
            GithubService githubService = RetroClient.getGithubService();

            Call<List<Following>> colListFollowing = githubService.getUserFollowing(Username);

            colListFollowing.enqueue(new Callback<List<Following>>() {
                @Override
                public void onResponse(Call<List<Following>> call, Response<List<Following>> response) {
                    try {
                        followingsList = (ArrayList<Following>) response.body();
                        getUserFollowers(user.getLogin());
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Snackbar.make(getCurrentFocus(), "No Following", Snackbar.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<Following>> call, Throwable t) {
                    Snackbar.make(getCurrentFocus(), "No Connection to server", Snackbar.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserFollowers (String Username) {
        try {
            String username = SearchActivity.user.getLogin();
            GithubService githubService = RetroClient.getGithubService();

            Call<List<Followers>> colListFollowers = githubService.getUserFollowers(Username);

            colListFollowers.enqueue(new Callback<List<Followers>>() {
                @Override
                public void onResponse(Call<List<Followers>> call, Response<List<Followers>> response) {
                    try {
                        followersList = (ArrayList<Followers>) response.body();
                        progressDialog.dismiss();
                        userNameText.setText("");
                        startActivity(new Intent(SearchActivity.this, MainActivity.class));
                    } catch (Exception e) {
                        progressDialog.dismiss();
                        Snackbar.make(getCurrentFocus(), "No Followers", Snackbar.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<Followers>> call, Throwable t) {
                    Snackbar.make(getCurrentFocus(), "No Connection to server", Snackbar.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}