package com.example.menginar.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.menginar.user.database.AppDatabase;
import com.example.menginar.user.model.Personel;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textViewTitle;

    EditText editTextFullName, editTextEmail, editTextPhone, editTextMSubject;
    Button buttonRegisterUser;
    FloatingActionButton floatingActionButton;
    AppDatabase appDatabase;
    Personel mPersonel;
    private static final int DELAY_MILLIS = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMainActivity();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getAppDatabaseConnect(getBaseContext());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), PersonelListActivity.class));

                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
            }
        });

        buttonRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getControlInfoUser();
            }
        });
    }

    public void getAppDatabaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    public void initMainActivity() {
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextMSubject = (EditText) findViewById(R.id.editTextMSubject);
        buttonRegisterUser = (Button) findViewById(R.id.buttonRegisterUser);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void getSpace() {
        editTextFullName.setText("");
        editTextEmail.setText("");
        editTextPhone.setText("");
        editTextMSubject.setText("");
    }

    public void getControlInfoUser() {
        try {
            String errorMessage = "Gerekli Alanları Doldurunuz!";
            String errorPhone = "Telefon Numarası Eksik veya Fazla!";
            String errorEmail = "Email Adresi Geçersiz!";
            String errorInsert = "Ekleme İşlemi Başarısız!";

            String fullName = editTextFullName.getText().toString();
            String email = editTextEmail.getText().toString();
            String phoneNumber = editTextPhone.getText().toString();
            String message = editTextMSubject.getText().toString();

            if (fullName.isEmpty()) {
                Snackbar.make(getCurrentFocus(), errorMessage, Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (phoneNumber.length() != 11) {
                Snackbar.make(getCurrentFocus(), errorPhone, Snackbar.LENGTH_SHORT).show();
                return;
            } else {
                if (phoneNumber.isEmpty()) {
                    Snackbar.make(getCurrentFocus(), errorMessage, Snackbar.LENGTH_SHORT).show();
                    return;
                }
            }

            if (!email.isEmpty()) {
                if (!isControlEmail(email)) {
                    Snackbar.make(getCurrentFocus(), errorEmail, Snackbar.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Snackbar.make(getCurrentFocus(), errorMessage, Snackbar.LENGTH_SHORT).show();
                return;
            }

            try {

                mPersonel = new Personel();
                mPersonel.fullName = fullName;
                mPersonel.emailAdress = email;
                mPersonel.phoneNumber = phoneNumber;
                mPersonel.message = message;

                appDatabase.personelDao().insertPersonel(mPersonel);
                getSpace();
            } catch (Exception e) {
                Log.d("database insert : ", e.getMessage());
                Snackbar.make(getCurrentFocus(), errorInsert, Snackbar.LENGTH_SHORT).show();
                return;
            }


        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        Snackbar.make(getCurrentFocus(), "İşlem Başarılı", Snackbar.LENGTH_SHORT).show();
    }


    public void getPersoneAll() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            List<Personel> personelList = appDatabase.personelDao().getAllPersonel();
            for (Personel personel : personelList) {
                stringBuilder.append(String.format(Locale.US,
                        "%s, %s, %s, %s \n", personel.fullName, personel.emailAdress, personel.phoneNumber, personel.message));
            }

        } catch (Exception e) {
            e.getMessage();
        }

        textViewTitle.setText(stringBuilder.toString());
    }

    public boolean isControlEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
