package work.vuong.template.screen.adduser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import work.vuong.template.R;
import work.vuong.template.common.activity.AbstractActivity;
import work.vuong.template.common.injection.AppComponent;
import work.vuong.template.common.model.GithubSearch;
import work.vuong.template.common.model.GithubUser;
import work.vuong.template.common.store.GithubStore;
import work.vuong.template.common.util.RxUtil;
import work.vuong.template.common.util.diff.DefaultDiffCallback;
import work.vuong.template.databinding.ActivityAddUserBinding;

public class AddUserActivity extends AbstractActivity<ActivityAddUserBinding> {

    private static final String TAG = "AddUserActivity";
    private Subscription textSubscription;
    private GithubUserAdapter userAdapter;
    //Injected
    @Inject GithubStore githubStore;

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_user;
    }

    @Override
    protected void inject(@NonNull AppComponent component) {
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userAdapter = new GithubUserAdapter(new ArrayList<>());
        userAdapter.setItemClickListener(user -> {
            githubStore.saveUser(user);
            Toast.makeText(this, R.string.user_added, Toast.LENGTH_SHORT).show();
            finish();
        });

        getBinding().recycler.setLayoutManager(new LinearLayoutManager(this));
        getBinding().recycler.setAdapter(userAdapter);
        getBinding().recycler.setHasFixedSize(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        textSubscription = RxTextView.textChanges(getBinding().searchQuery)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .flatMap(c -> githubStore.githubSearchUser(c.toString()).subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showResults, Throwable::printStackTrace);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxUtil.unsubscribe(textSubscription);
    }

    private void showResults(GithubSearch<GithubUser> gitHubUserGithubSearch) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DefaultDiffCallback<GithubUser>(userAdapter.getItems(), gitHubUserGithubSearch.getResults()) {
            @Override
            public boolean hasSameContent(GithubUser oldItem, GithubUser newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean isSameItem(GithubUser oldItem, GithubUser newItem) {
                return oldItem.getId() == newItem.getId();
            }
        });
        userAdapter.setItems(gitHubUserGithubSearch.getResults());
        diffResult.dispatchUpdatesTo(userAdapter);
    }
}