package apiservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Menginar on 26.3.2017.
 */

public class RetroClient {
    public static final String BASE_URL = "https://api.github.com/";

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static GithubService getGithubService() {
        return getRetrofitInstance().create(GithubService.class);
    }


}
