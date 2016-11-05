package work.vuong.template.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import work.vuong.template.R;
import work.vuong.template.common.activity.AbstractActivity;
import work.vuong.template.common.injection.AppComponent;
import work.vuong.template.common.util.ActivityUtil;
import work.vuong.template.common.util.NetworkUtil;
import work.vuong.template.common.util.RxUtil;
import work.vuong.template.databinding.ActivityHomeBinding;

/**
 * Only activity it displays the time.
 */
public class HomeActivity extends AbstractActivity<ActivityHomeBinding> {

    private static final String TAG = "HomeActivity";
    private Subscription subscription, subscriptionImage;
    private int count = 0;

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
        ActivityUtil.hideSystemUI(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setNewImage();
        // Create an observable dat sets another cat image every 30 minutes.
        subscriptionImage = Observable.timer(30, TimeUnit.MINUTES)
                .repeat()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    setNewImage();
                }, Throwable::printStackTrace);

        // Create an observable that returns a ping every second.
        subscription = Observable.timer(1, TimeUnit.SECONDS)
                .flatMap(l -> NetworkUtil.getPing())
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ping -> {
                    if (ping > 0) {
                        getBinding().pingText.setText(getString(R.string.ping_text, ping));
                    } else {
                        getBinding().pingText.setText(R.string.ping_error);
                    }
                }, Throwable::printStackTrace);
    }

    private void setNewImage() {
        Picasso.with(this).load(getString(R.string.cat_image_url, ++count))
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(getBinding().image);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxUtil.unsubscribe(subscription, subscriptionImage);
    }
}
