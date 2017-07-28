package org.proverbio.android.util;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * @author Juan Pablo Proverbio <proverbio@nowcreatives.co>
 */
public class Utils
{
    private Utils()
    {
        super();
    }

    public static void hideKeyboard( View view, LayoutInflater inflater )
    {
        InputMethodManager in = ( InputMethodManager ) inflater.getContext().getSystemService( Context.INPUT_METHOD_SERVICE );
        in.hideSoftInputFromWindow( view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
    }

    /**
     * Gets the max columns number that fits within screen and required width
     * @param context
     * @param width
     * @return
     */
    public static int getMaxColumnsForScreen(AppCompatActivity context, int width)
    {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = context.getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        return Math.round(dpWidth/width);
    }

    public static float getDisplayWidth( Activity context )
    {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics( outMetrics );

        float density  = context.getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;

        return dpWidth;
    }

    public static Pair<Float, Float> getDisplayWidthAndHeight( Activity context )
    {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics( outMetrics );

        float density  = context.getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        float dpHeight = outMetrics.heightPixels / density;

        return new Pair<>(dpWidth, dpHeight);
    }

    public static Pair<Float, Float> getDisplayWidthAndHeight( Activity context, float paddingPx )
    {
        Pair<Float, Float> measures = getDisplayWidthAndHeight(context);
        return new Pair<>(measures.first - paddingPx, measures.second - paddingPx);
    }

    public static boolean isTabletDevice( Activity context )
    {
        return ( ( context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK )
                >= Configuration.SCREENLAYOUT_SIZE_LARGE ) && getDisplayWidth( context ) > 650;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
