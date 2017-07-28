package org.proverbio.android.context;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * @author Juan Pablo Proverbio <proverbio@nowcreatives.co>
 */
public class ApplicationContext extends Application
{
    /**
     * The Application instance
     */
    private static volatile ApplicationContext instance;

    /**
     * A weak reference to the visible activity
     */
    private WeakReference<AppCompatActivity> visibleActivity;

    /**
     * The app has been created
     */
    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

    /**
     * The app is about to finish
     */
    @Override
    public void onTerminate()
    {
        instance = null;
        super.onTerminate();
    }

    /**
     * Returns this instance
     * @return
     */
    public static ApplicationContext getInstance()
    {
        return instance;
    }

    /**
     * Returns the visible activity instance
     * @return
     */
    public AppCompatActivity getVisibleActivity()
    {
        return visibleActivity == null ? null : visibleActivity.get();
    }

    /**
     * Updates the Application references to the visible activity
     * Null if there are no visible activity
     * @param visibleActivity
     */
    public void setVisibleActivity(AppCompatActivity visibleActivity)
    {
        if (visibleActivity != null)
        {
            this.visibleActivity = new WeakReference<>(visibleActivity);
        }
        else
        {
            this.visibleActivity = null;
        }
    }
}
