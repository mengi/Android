package com.example.android.materialdesigninstagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import connect.InstagramApiInterface;
import connect.RetroClient;
import model.InstagramData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ss on 27.3.2017.
 */

public class SearchActivity extends Activity {

    EditText searchText;
    Button searchButton;
    InstagramApiInterface api;
    public static InstagramData instagramData;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchText = (EditText) findViewById(R.id.input_name);
        searchButton = (Button) findViewById(R.id.btn_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSearchText()){
                    String username = searchText.getText().toString();
                    getUserInstagramData(username);
                } else {
                    Snackbar.make(getCurrentFocus(), "Bos olmaz", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isSearchText() {
        String username = searchText.getText().toString();
        if (username.isEmpty()) {
            return false;
        }

        return true;
    }

    public void getUserInstagramData(String username) {
        try {
            api = RetroClient.InstagramServive();
            Call<InstagramData> call = api.getUserInstagramData(username);
            call.enqueue(new Callback<InstagramData>() {
                @Override
                public void onResponse(Call<InstagramData> call, Response<InstagramData> response) {
                    instagramData = response.body();

                    if (instagramData.getItems().size() == 0) {
                        Snackbar.make(getCurrentFocus(), "Foto Yok", Snackbar.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(SearchActivity.this, MainActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<InstagramData> call, Throwable t) {
                    Snackbar.make(getCurrentFocus(), "balantı yok", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {

        }
    }
}
