package com.example.javier.spoonplayers;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.javier.spoonplayers.Connect.GroupApiService;
import com.example.javier.spoonplayers.Connect.RetroClient;
import com.example.javier.spoonplayers.Model.Message;
import com.example.javier.spoonplayers.Utils.ErrorMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Menginar on 3.4.2017.
 */
public class MessageActivity extends ActionBarActivity {
    final Context context = this;
    Toolbar toolbar;
    EditText editTextFullName, editTextEmail, editTextPhone, editTextMSubject;
    Button buttonSendMessage;
    Intent intent;
    FrameLayout statusBar;

    GroupApiService groupApiService;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
        toolbarStatusBar();
        navigationBarStatusBar();
        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessages();
            }
        });
    }

    public void getSpace() {
        editTextFullName.setText("");
        editTextEmail.setText("");
        editTextPhone.setText("");
        editTextMSubject.setText("");
    }

    public void SendMessages() {

        String FullName = editTextFullName.getText().toString();
        String Emails = editTextEmail.getText().toString();
        String Phones = editTextPhone.getText().toString();
        String Messages = editTextMSubject.getText().toString();

        String Contentx = "Gerekli Alanları Doldurunuz !!!";
        String PhoneMessage = "11 Haneli Telefon Numarasını Giriniz !!!";
        String EmailMessage = "Email Adresini Doğru Giriniz !!!";

        if (FullName.isEmpty()) {
            Toast.makeText(MessageActivity.this, Contentx, Toast.LENGTH_SHORT).show();
            return;
        }

        if (Emails.isEmpty()) {
            Toast.makeText(MessageActivity.this, Contentx, Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!EmailControl(Emails)) {
                Toast.makeText(MessageActivity.this, EmailMessage, Toast.LENGTH_SHORT).show();
            }
        }

        if (Phones.isEmpty()) {
            Toast.makeText(MessageActivity.this, Contentx, Toast.LENGTH_SHORT).show();
            return;
        } else {
            if ((Phones.length() < 11)) {
                Toast.makeText(MessageActivity.this, PhoneMessage, Toast.LENGTH_SHORT).show();
            }
        }

        if (Messages.isEmpty()) {
            Toast.makeText(MessageActivity.this, Contentx, Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = new Date();
        String DateTime = dateformat.format(date);

        getProgressBarDialog();

        if (ErrorMessage.isConnection(this)) {
            insertMessage(FullName, Emails, Phones, Messages, DateTime);
        } else {
            progressDoalog.dismiss();
            ErrorMessage.isConnection(context);
        }


    }

    public void insertMessage(String FullName, String Email, String Phone, String Subject, String Date) {

        try {
            groupApiService = RetroClient.getGroupApiService();
            Call<Message> call = groupApiService.getInsertMessaging(FullName, Email, Phone, Subject, Date);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    try {
                        if (response.body().getSuccess() == 1) {
                            progressDoalog.dismiss();
                            getSpace();
                            Toast.makeText(getApplication(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        if (response.body().getSuccess() == 0) {
                            progressDoalog.dismiss();
                            getSpace();
                            Toast.makeText(getApplication(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        progressDoalog.dismiss();
                        getSpace();
                        ErrorMessage.getErrorMessageString(getBaseContext(), "İşlem Gerçekleştirilemiyor");
                    }
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    progressDoalog.dismiss();
                    getSpace();
                    ErrorMessage.getErrorMessageString(getBaseContext(), "İşlem Gerçekleştirilemiyor");

                }
            });

        } catch (Exception e) {
            progressDoalog.dismiss();
            getSpace();
            ErrorMessage.getErrorMessageString(getBaseContext(), "İşlem Gerçekleştirilemiyor");

        }

    }

    public boolean EmailControl(String Email) {
        boolean state = false;
        for (int i = 0; i < Email.length(); i++) {
            if (Email.charAt(i) == '@') {
                state = true;
                break;
            }
        }

        return state;
    }

    public void toolbarStatusBar() {
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mesaj");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void navigationBarStatusBar() {

        // Fix portrait issues
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Fix issues for KitKat setting Status Bar color primary
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                MessageActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }

            // Fix issues for Lollipop, setting Status Bar color primary dark
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue21 = new TypedValue();
                MessageActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue21, true);
                final int color = typedValue21.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
                getWindow().setStatusBarColor(color);
            }
        }

        // Fix landscape issues (only Lollipop)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                MessageActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue = new TypedValue();
                MessageActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
                final int color = typedValue.data;
                getWindow().setStatusBarColor(color);
            }
        }
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(MessageActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void init() {
        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextMSubject = (EditText) findViewById(R.id.editTextMSubject);
        buttonSendMessage = (Button) findViewById(R.id.buttonSendMessage);
    }

    public void getProgressBarDialog() {
        try {
            progressDoalog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
            progressDoalog.setMessage("Gönderiliyor ...");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();
        } catch (Exception e) {
            ErrorMessage.getErrorMessageString(getBaseContext(), "Gönderilemdi");
        }
    }


}
