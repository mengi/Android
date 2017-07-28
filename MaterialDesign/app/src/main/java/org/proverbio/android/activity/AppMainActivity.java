package org.proverbio.android.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.proverbio.android.context.SharedPreferencesManager;
import org.proverbio.android.fragment.ImagesGridFragment;
import org.proverbio.android.material.R;

/**
 * @author Juan Pablo Proverbio <proverbio@nowcreatives.co>
 */
public class AppMainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    /**
     * The isDrawerLearn key used to save value to prefs.
     */
    private static final String DRAWER_LEARNT = "drawer_learnt";

    /**
     * The drawer position key used to save value to prefs
     */
    private static final String STATE_SELECTED_POSITION = "drawer_position";

    /**
     * The DrawerLayout
     */
    private DrawerLayout drawerLayout;

    /**
     * The NavigationView
     */
    private NavigationView navigationView;

    /**
     * The ActionBarDrawerToggle that connects {@see android.support.v7.widget.Toolbar} and {@see DrawerLayout}
     */
    private ActionBarDrawerToggle actionBarDrawerToggle;

    /**
     * A flag used to tell if the user has learnt the drawer
     */
    private boolean isNavigationDrawerLearnt;

    /**
     * The current {@see NavigationView} position
     */
    private int drawerSelectedPosition;

    /**
     * A floating action button
     */
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, getToolbar(), R.string.drawer_open, R.string.drawer_close)
        {
            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);

                if (!isNavigationDrawerLearnt)
                {
                    isNavigationDrawerLearnt = true;
                    SharedPreferencesManager.setPreferenceValue(AppMainActivity.this, DRAWER_LEARNT, true);
                }
                invalidateOptionsMenu();
            }
        };
        actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        isNavigationDrawerLearnt = SharedPreferencesManager.getPreferenceValue(this, DRAWER_LEARNT, Boolean.class);

        if (!isNavigationDrawerLearnt)
            this.drawerLayout.openDrawer(Gravity.LEFT);
            this.drawerLayout.post(new Runnable()
            {
                @Override
                public void run()
                {
                    actionBarDrawerToggle.syncState();
                }
            });

        this.drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //Inflating {@see FloatingActionButton}
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(this);

        if (savedInstanceState != null)
        {
            drawerSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }

        onNavigationItemSelected(navigationView.getMenu().getItem(drawerSelectedPosition));
    }

    @Override
    public void showBackButton()
    {
        super.showBackButton();

        if (actionBarDrawerToggle != null && drawerLayout != null)
        {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public void showDrawerButton()
    {
        super.showDrawerButton();

        if (actionBarDrawerToggle != null && drawerLayout != null)
        {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            actionBarDrawerToggle.syncState();
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_main_topdrawer;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.profileLayout:
                Toast.makeText(this, getString(R.string.profile), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);

                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        Fragment fragment = null;
        String fragmentTag = "";

        switch (menuItem.getItemId())
        {
            case R.id.image_grid:
                fragment = getSupportFragmentManager().findFragmentByTag(ImagesGridFragment.TAG);
                fragmentTag = ImagesGridFragment.TAG;
                if (fragment == null)
                {
                    fragment = new ImagesGridFragment();
                }
                break;
        }

        drawerLayout.closeDrawer(Gravity.LEFT);

        if ( fragment != null )
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout);
            transaction.replace(R.id.view_container, fragment, fragmentTag);
            transaction.addToBackStack(fragmentTag);
            transaction.commit();
        }

        return true;
    }

    public FloatingActionButton getFloatingActionButton()
    {
        return floatingActionButton;
    }
}
