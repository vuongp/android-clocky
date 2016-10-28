package work.vuong.template.common.injection;

import javax.inject.Singleton;

import dagger.Component;
import work.vuong.template.common.activity.AddUserActivity;
import work.vuong.template.screen.home.MainActivity;

/**
 * Created by vuongp on 11-10-16.
 *
 * Main component for the app
 */
@Singleton
@Component(modules = {
        AppModule.class,
        NetModule.class,
        StoreModule.class
})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(AddUserActivity addUserActivity);
}
