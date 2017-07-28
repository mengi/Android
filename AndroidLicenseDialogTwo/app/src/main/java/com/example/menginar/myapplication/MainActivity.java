package com.example.menginar.myapplication;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.LicensesDialogFragment;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.GnuLesserGeneralPublicLicense21;
import de.psdev.licensesdialog.licenses.License;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void onMultipleIncludeOwnClick(final View view) {
        new LicensesDialog.Builder(this)
                .setNotices(R.raw.deneme)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }

    public void onMultipleProgrammaticClick(final View view) {
        final Notices notices = new Notices();
        notices.addNotice(new Notice("Retrofit", "http://square.github.io/retrofit/", "Jake Wharton", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Picasso", "http://square.github.io/picasso/", "Jake Wharton", new ApacheSoftwareLicense20()));

        new LicensesDialog.Builder(this)
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }
}
