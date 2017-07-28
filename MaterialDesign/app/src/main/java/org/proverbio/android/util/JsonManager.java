package org.proverbio.android.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;

/**
 * @author Juan Pablo Proverbio
 */
public class JsonManager
{
    private static final Gson GSON = new GsonBuilder().create();

    private JsonManager()
    {
        super();
    }

    public static String toJSON( Object object )
    {
        return GSON.toJson( object );
    }

    public static <T> T fromJSON( String object, Class<T> classObject )
    {
        return GSON.fromJson( object, classObject );
    }

    public static Object fromJSON( String object, Type type )
    {
        return GSON.fromJson( object, type );
    }
}
