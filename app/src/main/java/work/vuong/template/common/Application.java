package work.vuong.template.common;

import android.support.annotation.NonNull;

import io.realm.Realm;
import work.vuong.template.common.injection.AppComponent;
import work.vuong.template.common.injection.AppModule;
import work.vuong.template.common.injection.DaggerAppComponent;

/**
 * Created by vuongp on 11-10-16.
 *
 *
 */
public class Application extends android.app.Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent(@NonNull android.app.Application application){
        return ((Application) application).appComponent;
    }
}