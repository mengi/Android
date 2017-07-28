package com.example.photomap.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.photomap.R;
import com.example.photomap.connection.UserRetrofitClient;
import com.example.photomap.connection.UserService;
import com.example.photomap.model.Message;
import com.example.photomap.model.User;
import com.example.photomap.util.SharedPreferencesUtil;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.photomap.util.GeneralUtil.BitmapToString;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_ACCESS_TOKEN;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_BIRTHDAY;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_EMAIL;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_GENDER;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_ID;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_NAME;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_URL;
import static com.example.photomap.util.SharedPreferencesUtil.deleteFromSharedPrefs;

public class LoginActivity extends AppCompatActivity {
    LoginButton facebookLoginButton;
    CallbackManager callbackManager;
    public static final String LOGOUT = "logout";
    UserService userService;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        final View view = getWindow().getDecorView().getRootView();

        callbackManager = CallbackManager.Factory.create();

        facebookLoginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions(Arrays.asList("email"));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(LOGOUT)) {
            facebookLogOut();
        }

        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            //Toast.makeText(LoginActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }

        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphLoginRequest(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                try {
                    Snackbar.make(view, "Login Cancelled", Snackbar.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(FacebookException exception) {
                try {
                    Snackbar.make(view, "Not Connected", Snackbar.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    protected void GraphLoginRequest(final AccessToken accessToken) {
        try {
            GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                            try {
                                user = new User();
                                user.setUserId(jsonObject.getString("id"));
                                user.setUserName(jsonObject.getString("name"));
                                user.setUserEmail(jsonObject.getString("email"));
                                user.setUserGender(jsonObject.getString("gender"));
                                user.setUserBirthday(jsonObject.getString("birthday"));
                                user.setUserUrl("http://graph.facebook.com/" + jsonObject.getString("id") + "/picture?type=large");
                                user.setAccessToken(accessToken.getToken().toString());

                                try {
                                    getImage(user.getUserUrl());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });
            Bundle bundle = new Bundle();
            bundle.putString(
                    "fields",
                    "id,name,link,email,birthday,gender,last_name,first_name,locale,timezone,updated_time,verified"
            );
            graphRequest.setParameters(bundle);
            graphRequest.executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(LoginActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(LoginActivity.this);
    }

    public void facebookLogOut() {
        LoginManager.getInstance().logOut();
        clearLoginData();
    }

    private void clearLoginData() {
        deleteFromSharedPrefs(LoginActivity.this, PRE_ACCESS_TOKEN);
        deleteFromSharedPrefs(LoginActivity.this, PRE_EMAIL);
        deleteFromSharedPrefs(LoginActivity.this, PRE_BIRTHDAY);
        deleteFromSharedPrefs(LoginActivity.this, PRE_NAME);
        deleteFromSharedPrefs(LoginActivity.this, PRE_GENDER);
        deleteFromSharedPrefs(LoginActivity.this, PRE_ID);
        deleteFromSharedPrefs(LoginActivity.this, PRE_URL);
    }

    public void getImage(String imageUrl) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(imageUrl)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println("request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                try {
                    response.body().byteStream(); // Read the data from the stream
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    SharedPreferencesUtil.setToSharedPreferences(LoginActivity.this, PRE_URL, BitmapToString(bitmap));
                    getLogin();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getLogin() {
        userService = UserRetrofitClient.getApiService();
        try {
            Call<Message> call = userService.getUserInsert(user.getAccessToken(), user.getUserName(), user.getUserEmail(), user.getUserBirthday(), user.getUserGender(), user.getUserUrl(), user.getUserId());
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    SharedPreferencesUtil.setToSharedPreferences(LoginActivity.this, PRE_ID, user.getUserId());
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }*/
}
