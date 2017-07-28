package connect;

import model.InstagramData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ss on 27.3.2017.
 */

public interface InstagramApiInterface {
    // Metodlar

    @GET("/{username}/media/")
    Call<InstagramData> getUserInstagramData(@Path("username") String username);
}
