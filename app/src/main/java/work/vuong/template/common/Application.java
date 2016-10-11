package work.vuong.template.common;

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

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent(android.app.Application application){
        return ((Application) application).appComponent;
    }
}