package work.vuong.template.common.net;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;
import work.vuong.template.common.model.GithubUser;
import work.vuong.template.common.model.GithubSearch;

/**
 * Created by vuongp on 11-10-16.
 *
 * Api service for github
 */
public interface GitHubService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/search/users")
    Observable<GithubSearch<GithubUser>> searchUsers(@Query("q") String query);

}
