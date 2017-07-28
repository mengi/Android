package org.proverbio.android.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import org.proverbio.android.material.R;

/**
 * @author Juan Pablo Proverbio <proverbio8@nowcreatives.co/>
 */
public class DialogUtils
{
    private DialogUtils()
    {
        super();
    }

    public static void showMessageOkCancel( Activity activity, String message, DialogInterface.OnClickListener okListener )
    {
        if ( activity == null )
        {
            return;
        }

        new AlertDialog.Builder( activity )
                .setMessage( message )
                .setPositiveButton( R.string.ok_button_label, okListener )
                .setNegativeButton( R.string.cancel_button_label, null )
                .create()
                .show();
    }

    public static void showMessageOkCancel( Activity activity, String message, DialogInterface.OnClickListener okListener,
                                            DialogInterface.OnClickListener cancelListener )
    {
        if ( activity == null )
        {
            return;
        }

        new AlertDialog.Builder( activity )
                .setMessage( message )
                .setPositiveButton( R.string.ok_button_label, okListener)
                .setNegativeButton( R.string.cancel_button_label, cancelListener )
                .create()
                .show();
    }

    public static void showOkDialog(Activity activity, int titleResId, String message)
    {
        if ( activity == null )
        {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder( activity );
        builder.setTitle(titleResId);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok_button_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
