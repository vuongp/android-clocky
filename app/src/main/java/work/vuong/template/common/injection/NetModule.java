package work.vuong.template.common.injection;

import android.content.Context;
import android.support.compat.BuildConfig;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import work.vuong.template.R;
import work.vuong.template.common.net.GitHubService;

/**
 * Created by vuongp on 11-10-16.
 *
 * Network Module for any services that needs an internet connection.
 */
@Module
class NetModule {

    private static final int CACHE_SIZE_IN_MB = 10;

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofit(Context context){
        //setup cache
        File cacheDir = new File(context.getCacheDir(), "responses");
        int cacheSize = CACHE_SIZE_IN_MB * 1024 * 1024;

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .cache(new Cache(cacheDir, cacheSize));

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        return new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
    }

    @Provides
    @Singleton
    GitHubService provideGithubService(Context context, Retrofit.Builder retrofit){
        return retrofit.baseUrl(context.getString(R.string.github_endpoint)).build().create(GitHubService.class);
    }

}
