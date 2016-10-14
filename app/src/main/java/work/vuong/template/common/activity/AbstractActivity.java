package work.vuong.template.common.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import work.vuong.template.common.Application;
import work.vuong.template.common.injection.AppComponent;

/**
 * Created by vuongp on 11-10-16.
 *
 * AbstractActivity to be extended by all, makes use of databinding
 */
public abstract class AbstractActivity<B extends ViewDataBinding> extends AppCompatActivity {

    private B binding;

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * Method to inject the appcomponent to make use of the inject annotation
     * @param component
     */
    protected abstract void inject(@NonNull AppComponent component);

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        inject(getComponent());
        binding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    protected B getBinding() {
        return binding;
    }

    @NonNull
    private AppComponent getComponent(){
        return Application.getAppComponent(getApplication());
    }
}
