package com.example.photomap.ui.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.photomap.R;
import com.example.photomap.connection.UserRetrofitClient;
import com.example.photomap.connection.UserService;
import com.example.photomap.model.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.photomap.ui.activity.LoginActivity.user;

/**
 * Created by ss on 22.7.2017.
 */

public class ComplainFragment extends Fragment {

    private EditText editUserName, editUserEmail, editTitle, editMessage;
    private Button button;
    private String userName, userEmail, userTitle, userMessage;
    private View view;
    UserService userService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_complain, container, false);
        initView();

        return view;
    }

    public void initView() {
        editUserName = (EditText) view.findViewById(R.id.nameEditText);
        editUserEmail = (EditText) view.findViewById(R.id.emailEditText);
        editTitle = (EditText) view.findViewById(R.id.titleEditText);
        editMessage = (EditText) view.findViewById(R.id.subjectEditText);
        button = (Button) view.findViewById(R.id.sendMessageButton);


        editUserName.setText(user.getUserName());
        editUserEmail.setText(user.getUserEmail());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    getInsertMessage();
                    return;
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;
        userName = editUserName.getText().toString();
        userEmail = editUserEmail.getText().toString();
        userTitle = editTitle.getText().toString();
        userMessage = editMessage.getText().toString();

        if (userName.isEmpty() || userName.length() < 3) {
            editUserName.setError("At least 3 characters");
            valid = false;
        } else {
            editUserName.setError(null);
        }

        if (userEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            editUserEmail.setError("Enter a valid email address.");
            valid = false;
        } else {
            editUserEmail.setError(null);
        }

        if (userTitle.isEmpty() || userTitle.length() < 4 || userTitle.length() > 25) {
            editTitle.setError("Between 4 and 25 alphanumeric characters.");
            valid = false;
        } else {
            editTitle.setError(null);
        }

        if (userMessage.isEmpty() || userMessage.length() < 4) {
            editMessage.setError("4 alphanumeric characters.");
            valid = false;
        } else {
            editMessage.setError(null);
        }
        return valid;
    }

    public void getInsertMessage() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            String dateString = dateFormat.format(new Date());

            userService = UserRetrofitClient.getApiService();
            Call<Message> messageCall = userService.getComplainMessage(userName, userEmail,
                    userTitle, userMessage, dateString);

            messageCall.enqueue(new Callback<Message>() {
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
                    Snackbar.make(getView(), "Connection Failed With Server", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
