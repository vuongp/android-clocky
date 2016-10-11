package work.vuong.template.common.activity;

import android.support.v7.app.AppCompatActivity;

import work.vuong.template.common.Application;
import work.vuong.template.common.injection.AppComponent;

/**
 * Created by vuongp on 11-10-16.
 *
 * Base activity to be extended by all.
 */
public class BaseActivity extends AppCompatActivity {

    public AppComponent getComponent(){
        return Application.getAppComponent(getApplication());
    }
}
