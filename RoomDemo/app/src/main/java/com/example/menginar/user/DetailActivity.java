package com.example.menginar.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.widget.ImageView;

import com.example.menginar.user.database.AppDatabase;
import com.example.menginar.user.databinding.ActivityPersonelDetailBinding;
import com.example.menginar.user.model.Personel;

import java.util.List;

/**
 * Created by menginar on 02.06.2017.
 */

public class DetailActivity extends AppCompatActivity {

    AppDatabase appDatabase;
    private ActivityPersonelDetailBinding binding;
    int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personel_detail);
        getAppDBaseConnect(getBaseContext());

        try {
            Bundle bundle = getIntent().getExtras();
            String value = bundle.getString("send_string");
            position = Integer.parseInt(value);

        } catch (Exception e) {

        }
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Detay");
        loadBackdrop();

        binding.setPersonel(getPersonel(position));

    }

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        imageView.setImageResource(R.mipmap.ic_launcher);
    }

    public Personel getPersonel(int position) {
        Personel personel = new Personel();
        try {
            List<Personel> personelList = appDatabase.personelDao().getAllPersonel();
            personel = personelList.get(position);
        } catch (Exception e) {

        }

        return personel;
    }

    public void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getInMemoryDatabase(context);
        } catch (Exception e) {

        }
    }
}
