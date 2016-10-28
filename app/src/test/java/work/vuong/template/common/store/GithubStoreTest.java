package work.vuong.template.common.store;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import io.realm.Realm;
import io.realm.log.RealmLog;
import work.vuong.template.common.model.GithubUser;
import work.vuong.template.common.net.GitHubService;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by oberon (vuongpham) on 28/10/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, Realm.class})
public class GithubStoreTest {

    @Mock private GitHubService gitHubService;
    private Realm mockRealm;

    private GithubStore githubStore;

    @Before
    public void setup() {
        mockStatic(Realm.class);
        MockitoAnnotations.initMocks(this);

        mockRealm = PowerMockito.mock(Realm.class);

        githubStore = new GithubStore(gitHubService, mockRealm);

        when(Realm.getDefaultInstance()).thenReturn(mockRealm);
    }

    @Test
    public void githubSearchUserEmpty() throws Exception {
        githubStore.githubSearchUser("");

        verify(gitHubService, never()).searchUsers(any());
    }

    @Test
    public void githubSearchUserNull() throws Exception {
        githubStore.githubSearchUser(null);

        verify(gitHubService, never()).searchUsers(any());
    }

    @Test
    public void githubSearchUser() throws Exception {
        String query = "Vuong";
        githubStore.githubSearchUser(query);

        verify(gitHubService, times(1)).searchUsers(query);
    }

    @Test
    public void saveUser() throws Exception {
        GithubUser githubUser = new GithubUser();
        githubStore.saveUser(githubUser);

        verify(mockRealm).copyToRealmOrUpdate(githubUser);
    }

    @Test
    public void saveUserNull() throws Exception {
        List<GithubUser> something = null;
        githubStore.saveUser(null);

        verify(mockRealm, never()).copyToRealmOrUpdate(something);
    }
}