package work.vuong.template.common.injection;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import work.vuong.template.common.Application;

/**
 * Created by vuongp on 11-10-16.
 *
 *  Application module that provides Application and Context
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication(){
        return application;
    }

    @Provides
    @Singleton
    Context providesContext(){
        return application;
    }

}
