package com.chatt.demo;

import android.app.Application;

import com.parse.Parse;

/**
 * The Class ChattApp is the Main Application class of this app. The onCreate
 * method of this class initializes the Parse.
 */
public class ChattApp extends Application
{

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();

		Parse.initialize(this, "aEK4rYnLSxU1AqvXc8Euil0Is8mklk7d4Y3YqC6i",
				"dYguCg65IT3J2gX2JeFhhDcB25RBjdpceveS8UdU");

	}
}
