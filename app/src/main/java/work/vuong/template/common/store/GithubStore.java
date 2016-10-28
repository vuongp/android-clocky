package work.vuong.template.common.store;

import android.text.TextUtils;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import work.vuong.template.common.model.GithubUser;
import work.vuong.template.common.model.GithubSearch;
import work.vuong.template.common.net.GitHubService;
import work.vuong.template.common.util.StringUtils;

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

    public Observable<GithubSearch<GithubUser>> githubSearchUser(String query){
        if (StringUtils.isEmpty(query)) {
            return Observable.just(GithubSearch.empty(GithubUser.class));
        }

        return gitHubService.searchUsers(query);
    }

    public void saveUser(GithubUser... users){
        if (users == null) return;

        realm.beginTransaction();
        for (GithubUser user : users) {
            realm.copyToRealmOrUpdate(user);
        }
        realm.commitTransaction();
    }

    public List<GithubUser> getSavedUsers() {
        return realm.where(GithubUser.class).findAll();
    }
}
