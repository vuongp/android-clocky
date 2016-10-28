package work.vuong.template.common.injection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import work.vuong.template.common.store.GithubStore;
import work.vuong.template.common.net.GitHubService;

/**
 * Created by vuongp on 14-10-16.
 *
 * Module for Anything that stores.
 */
@Module
public class StoreModule {

    @Provides
    @Singleton
    Realm provideRealm(){
       return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    GithubStore provideGitHubStore(GitHubService gitHubService, Realm realm){
        return new GithubStore(gitHubService, realm);
    }

}
