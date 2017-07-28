package com.example.menginar.user;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.example.menginar.user.adapter.PersonelAdapter;
import com.example.menginar.user.database.AppDatabase;
import com.example.menginar.user.databinding.ActivityPersonelDetailBinding;
import com.example.menginar.user.model.Personel;

import java.net.ConnectException;
import java.util.List;

/**
 * Created by menginar on 02.06.2017.
 */

public class PersonelListActivity extends AppCompatActivity {

    RecyclerView recyclerViewPersonal;
    AppDatabase appDatabase;
    PersonelAdapter personelAdapter;
    TypedValue typedValueToolbarHeight = new TypedValue();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personel_list);

        getAppDBaseConnect(getBaseContext());

        recyclerViewPersonal = (RecyclerView) findViewById(R.id.recyclerViewPersonal);
        personelAdapter = new PersonelAdapter(appDatabase.personelDao().getAllPersonel(), this);
        recyclerViewPersonal.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewPersonal.setAdapter(personelAdapter);
    }

    public void getAppDBaseConnect(Context context) {
        try {
            appDatabase = AppDatabase.getDatabaseBuilder(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
