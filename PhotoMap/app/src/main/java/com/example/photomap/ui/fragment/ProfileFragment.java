package com.example.photomap.ui.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.photomap.R;
import com.example.photomap.connection.UserRetrofitClient;
import com.example.photomap.connection.UserService;
import com.example.photomap.model.User;
import com.example.photomap.util.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.photomap.ui.activity.LoginActivity.user;
import static com.example.photomap.util.GeneralUtil.StringToBitmap;
import static com.example.photomap.util.GeneralUtil.transform;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_ID;
import static com.example.photomap.util.SharedPreferencesUtil.PRE_URL;

/**
 * Created by ss on 22.7.2017.
 */

public class ProfileFragment extends Fragment {

    private EditText editFullName, editEmail, editGender, editBirthday;
    private Button button;
    ImageView ımageView;
    UserService userService;
    String userFullName, userEmail, userGender, userBirthday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editFullName = (EditText) view.findViewById(R.id.input_name);
        editEmail = (EditText) view.findViewById(R.id.input_email);
        editBirthday = (EditText) view.findViewById(R.id.input_birthday);
        editGender = (EditText) view.findViewById(R.id.input_gender);
        ımageView = (ImageView) view.findViewById(R.id.input_image);
        button = (Button) view.findViewById(R.id.btn_update);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    User usertwo = new User();
                    usertwo.setUserId(SharedPreferencesUtil.getFromSharedPrefs(getActivity(), PRE_ID));
                    usertwo.setUserEmail(editEmail.getText().toString());
                    usertwo.setUserBirthday(editBirthday.getText().toString());
                    usertwo.setUserName(editFullName.getText().toString());
                    usertwo.setUserGender(editGender.getText().toString());
                    getUserUpdate(usertwo);
                    return;
                }
            }
        });
        getUser();
        return view;
    }

    public void getUser() {
        try {
            editFullName.setText(user.getUserName());
            editEmail.setText(user.getUserEmail());
            editBirthday.setText(user.getUserBirthday());
            editGender.setText(user.getUserGender());

            ımageView.setImageBitmap(transform(StringToBitmap(SharedPreferencesUtil.getFromSharedPrefs(getActivity(), PRE_URL))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserUpdate (final User userone) {
        try {
            userService = UserRetrofitClient.getApiService();
            Call<User> call = userService.getUserUpdate(userone.getUserId(), userone.getUserName(),
                    userone.getUserEmail(), userone.getUserBirthday(), userone.getUserGender());

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    getUser();
                    Snackbar.make(getView(), response.body().getMessage(),Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Snackbar.make(getView(), "Sunucuya Bağlantı Sağlanamadı",Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validate() {
        boolean valid = true;

        userFullName = editFullName.getText().toString();
        userEmail = editEmail.getText().toString();
        userBirthday = editBirthday.getText().toString();
        userGender = editGender.getText().toString();

        if (userFullName.isEmpty() || userFullName.length() < 3) {
            editFullName.setError("At least 3 characters");
            valid = false;
        } else {
            editFullName.setError(null);
        }

        if (userEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            editEmail.setError("Enter a valid email address.");
            valid = false;
        } else {
            editEmail.setError(null);
        }

        if (userBirthday.isEmpty() || userBirthday.length() < 6) {
            editBirthday.setError("Enter a valid email address.");
            valid = false;
        } else {
            editBirthday.setError(null);
        }

        if (userGender.isEmpty() || userGender.length() < 4 || userGender.length() > 10) {
            editGender.setError("Between 4 and 10 alphanumeric characters.");
            valid = false;
        } else {
            editGender.setError(null);
        }
        return valid;
    }
}
