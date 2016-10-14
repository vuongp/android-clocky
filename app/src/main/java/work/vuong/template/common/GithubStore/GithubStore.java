package work.vuong.template.common.GithubStore;

import io.realm.Realm;
import rx.Observable;
import work.vuong.template.common.model.GitHubUser;
import work.vuong.template.common.model.GithubSearch;
import work.vuong.template.common.net.GitHubService;

/**
 * Created by vuongp on 14-10-16.
 *
 * A class that manages the network calls, caches and stores content.
 */
public class GithubStore {

    private GitHubService gitHubService;
    private Realm realm;

    public GithubStore(GitHubService gitHubService, Realm realm) {
        this.gitHubService = gitHubService;
        this.realm = realm;
    }

    public Observable<GithubSearch<GitHubUser>> githubSearchUser(String query){
        return gitHubService.searchUsers(query);
    }

    public void saveUser(GitHubUser user){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
}
