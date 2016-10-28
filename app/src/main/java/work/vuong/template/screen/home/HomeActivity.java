package work.vuong.template.screen.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;

import javax.inject.Inject;

import work.vuong.template.R;
import work.vuong.template.common.store.GithubStore;
import work.vuong.template.common.activity.AbstractActivity;
import work.vuong.template.common.injection.AppComponent;
import work.vuong.template.databinding.ActivityHomeBinding;
import work.vuong.template.screen.adduser.AddUserActivity;

/**
 * Homescreen activity
 */
public class HomeActivity extends AbstractActivity<ActivityHomeBinding> {

    private static final String TAG = "HomeActivity";
    @Inject GithubStore githubStore;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void inject(@NonNull AppComponent component) {
        component.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(getBinding().appBar.toolbar);

        getBinding().appBar.fab.setOnClickListener(view -> showAddUser());
    }

    private void showAddUser() {
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }
}
