package com.android.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.myapplication.R;


/**
 * Created by menginar on 25.09.2017.
 */

public class FragmentComplaint extends Fragment {

    public View view;
    public EditText nameEditText, emailEditText, titleEditText, messageEditText;
    public Button sendMessageButton;

    private String userName, userEmail, userTitle, userMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_complaint, container, false);

        getInit();
        return view;
    }

    private void getInit() {
        sendMessageButton =(Button) view.findViewById(R.id.sendMessageButton);
        nameEditText = (EditText) view.findViewById(R.id.nameEditText);
        emailEditText = (EditText) view.findViewById(R.id.emailEditText);
        titleEditText = (EditText) view.findViewById(R.id.titleEditText);
        messageEditText = (EditText) view.findViewById(R.id.messageEditText);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    return;
                }
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        userName = nameEditText.getText().toString();
        userEmail = emailEditText.getText().toString();
        userTitle = titleEditText.getText().toString();
        userMessage = messageEditText.getText().toString();

        if (userName.isEmpty() || userName.length() < 3) {
            nameEditText.setError("At least 3 characters");
            valid = false;
        } else {
            nameEditText.setError(null);
        }

        if (userEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            emailEditText.setError("Enter a valid email address.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (userTitle.isEmpty() || userTitle.length() < 4 || userTitle.length() > 25) {
            titleEditText.setError("Between 4 and 25 alphanumeric characters.");
            valid = false;
        } else {
            titleEditText.setError(null);
        }

        if (userMessage.isEmpty() || userMessage.length() < 4) {
            messageEditText.setError("4 alphanumeric characters.");
            valid = false;
        } else {
            messageEditText.setError(null);
        }
        return valid;
    }

}
