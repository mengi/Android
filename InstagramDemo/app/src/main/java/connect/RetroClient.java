package connect;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ss on 27.3.2017.
 */

public class RetroClient {

    public static final String base_url = "https://www.instagram.com";

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static InstagramApiInterface InstagramServive(){
        return getRetrofit().create(InstagramApiInterface.class);
    }
}
