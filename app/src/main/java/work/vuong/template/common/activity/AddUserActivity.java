package work.vuong.template.common.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import work.vuong.template.R;
import work.vuong.template.common.GithubStore.GithubStore;
import work.vuong.template.common.injection.AppComponent;
import work.vuong.template.databinding.ActivityAddUserBinding;

public class AddUserActivity extends AbstractActivity<ActivityAddUserBinding> {

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

    }

}
