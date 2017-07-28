package com.example.javier.spoonplayers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.javier.spoonplayers.R;
import com.squareup.picasso.Picasso;

public class NoticeDetailActivity extends ActionBarActivity {

    final Context context = this;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    FrameLayout statusBar;

    ImageView imageViewNotice;
    TextView textViewNoticeSubject, textViewNoticeDate, textViewNoticeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("VALUES", MODE_PRIVATE);

        setContentView(R.layout.activity_notice_detail);
        toolbarStatusBar();
        navigationBarStatusBar();

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toolbarStatusBar() {
        statusBar = (FrameLayout) findViewById(R.id.statusBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.text_notice_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void navigationBarStatusBar() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                NoticeDetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }

            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue21 = new TypedValue();
                NoticeDetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue21, true);
                final int color = typedValue21.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
                getWindow().setStatusBarColor(color);
            }
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (Build.VERSION.SDK_INT >= 19) {
                TypedValue typedValue19 = new TypedValue();
                NoticeDetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue19, true);
                final int color = typedValue19.data;
                FrameLayout statusBar = (FrameLayout) findViewById(R.id.statusBar);
                statusBar.setBackgroundColor(color);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                TypedValue typedValue = new TypedValue();
                NoticeDetailActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
                final int color = typedValue.data;
                getWindow().setStatusBarColor(color);
            }
        }
    }

    public void init() {
        imageViewNotice = (ImageView) findViewById(R.id.imageViewNotice);
        textViewNoticeSubject = (TextView) findViewById(R.id.textViewNoticeSubject);

        textViewNoticeDate = (TextView) findViewById(R.id.textViewNoticeDate);
        textViewNoticeName = (TextView) findViewById(R.id.textViewNoticeName);

        textViewNoticeName.setText(sharedPreferences.getString("TITLE", ""));
        textViewNoticeDate.setText(sharedPreferences.getString("DATE", ""));
        textViewNoticeSubject.setText(sharedPreferences.getString("SUBJECT", ""));

        String imageUrl = sharedPreferences.getString("IMAGE", "");
        if (!imageUrl.isEmpty() && imageUrl != null) Picasso
                .with(context)
                .load(sharedPreferences.getString("IMAGE", ""))
                .into(imageViewNotice);
    }

}
