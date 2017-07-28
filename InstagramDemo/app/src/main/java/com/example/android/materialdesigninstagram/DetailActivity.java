package com.example.android.materialdesigninstagram;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        image = (ImageView) findViewById(R.id.instagram_image_detail);


        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        if (!SearchActivity.instagramData.getItems().get(postion).getCaption().getText().isEmpty()) {
            collapsingToolbar.setTitle(SearchActivity.instagramData.getItems().get(postion).getCaption().getText());
        } else {
            collapsingToolbar.setTitle(SearchActivity.instagramData.getItems().get(postion).getUser().getFullName());
        }

        Picasso.with(getApplicationContext())
                .load(SearchActivity.instagramData.getItems().get(postion).getImages().getLowResolution().getUrl())
                .into(image);
    }
}
