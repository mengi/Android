
package org.proverbio.android.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.proverbio.android.context.ApplicationContext;
import org.proverbio.android.material.R;

/**
 * @author Juan Pablo Proverbio <proverbio@nowcreatives.co>
 */
public abstract class BaseActivity extends AppCompatActivity
{
    private Toolbar toolbar;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        //Gets {@see Toolbar} instance from inflated layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Gets {@see FloatingActionButton} instance from inflated layout
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        if (toolbar != null)
        {
            //Sets our Toolbar instance as our application's ActionBar
            setSupportActionBar(toolbar);

            //Enables Home as Up - Arrow or Drawer icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onBackPressed();
                }
            });
        }
    }

    public void showBackButton()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showDrawerButton()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled( false );
    }

    @Override
    public void onResume()
    {
        ApplicationContext.getInstance().setVisibleActivity(this);
        super.onResume();
    }

    @Override
    public void onStop()
    {
        ApplicationContext.getInstance().setVisibleActivity(null);
        super.onStop();
    }

    protected abstract int getLayoutResource();

    public Toolbar getToolbar()
    {
        return toolbar;
    }

    public FloatingActionButton getFloatingActionButton()
    {
        return floatingActionButton;
    }
}
