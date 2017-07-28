package apiservice;

import java.util.List;

import model.Followers;
import model.Following;
import model.User;
import model.UserSearch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Menginar on 26.3.2017.
 */

public interface GithubService {

    // User
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    // Search
    @GET("search/users")
    Call<UserSearch> getUsersSearch(@Query("q") String username);

    // Followers
    @GET("users/{username}/followers")
    Call<List<Followers>> getUserFollowers(@Path("username") String username);

    //Following
    @GET("users/{username}/following")
    Call<List<Following>> getUserFollowing(@Path("username") String username);

}
