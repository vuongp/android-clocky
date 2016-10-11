package work.vuong.template.common.injection;

import javax.inject.Singleton;

import dagger.Component;
import work.vuong.template.MainActivity;

/**
 * Created by vuongp on 11-10-16.
 *
 * Main component for the app
 */
@Singleton
@Component(modules = {
        AppModule.class,
        NetModule.class
})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
