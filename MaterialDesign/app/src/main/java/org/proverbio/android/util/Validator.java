package org.proverbio.android.util;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * @author Juan Pablo Proverbio
 */
public class Validator
{
    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "^[0-9]{8,}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,}$";
    // Error Messages
    public static final String REQUIRED_MSG = "Required";
    public static final String INVALID_LENGHT = "Min is ";
    public static final String EMAIL_MSG = "Invalid email";
    public static final String PHONE_MSG = "Invalid phone";
    public static final String WEBSITE_MSG = "Invalid website";
    private static final String PASSWORD_MSG = "Must be min 6 characters and only alphanumeric";
    private static final String PASSWORD_CONFIRMATION_MSG = "Passwords don't match";

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid( EditText editText, String regex, String errMsg, boolean required )
    {
        String text = editText.getText().toString().trim();
        editText.setError( null );

        if ( required )
        {
            if ( !hasText( editText ) )
            {
                return false;
            }

            if ( !Pattern.matches( regex, text ) )
            {
                editText.setError( errMsg );
                return false;
            }
        }
        else if ( !TextUtils.isEmpty( editText.getText() ) && !Pattern.matches( regex, text ) )
        {
            editText.setError( errMsg );
            return false;
        }

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText( EditText editText )
    {
        String text = editText.getText().toString().trim();
        editText.setError( null );

        // length 0 means there is no text
        if ( text.length() == 0 )
        {
            editText.setError( REQUIRED_MSG );
            return false;
        }

        return true;
    }

    public static boolean hasText( TextView textView )
    {
        String text = textView.getText().toString().trim();
        textView.setError( null );

        // length 0 means there is no text
        if ( text.length() == 0 )
        {
            textView.setError( REQUIRED_MSG );
            return false;
        }

        return true;
    }

    public static boolean hasText( TextView textView, int minLenght )
    {
        String text = textView.getText().toString().trim();
        textView.setError(null);

        if ( text.length() == 0 )
        {
            textView.setError( REQUIRED_MSG );
            return false;
        }

        if ( text.length() < minLenght )
        {
            textView.setError( INVALID_LENGHT + minLenght );
            return false;
        }

        return true;
    }

    public static boolean hasText( EditText editText, int minLenght )
    {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if ( text.length() == 0 )
        {
            editText.setError( REQUIRED_MSG );
            return false;
        }

        if ( text.length() < minLenght )
        {
            editText.setError( INVALID_LENGHT + minLenght );
            return false;
        }

        return true;
    }
}

