package work.vuong.template.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.sql.Time;
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
import work.vuong.template.common.util.ViewUtil;
import work.vuong.template.databinding.ActivityHomeBinding;

/**
 * Only activity it displays the time.
 */
public class HomeActivity extends AbstractActivity<ActivityHomeBinding> {

    private Subscription subscription, subscriptionImage, subscriptionTap;

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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setNewImage();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getBinding().frame.setOnClickListener(v -> {
            setNewImage();
            RxUtil.unsubscribe(subscriptionTap);
            subscriptionTap = ViewUtil.disableView(getBinding().frame, 1, TimeUnit.SECONDS);
        });

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
                .retry()
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
        //Get the image at half the resolution so it takes less network calls
        int width = getVariable(getBinding().getRoot().getWidth() / 2, 100);
        int height = getVariable(getBinding().getRoot().getHeight() / 2, 100);

        Picasso.with(this).load(getString(R.string.cat_image_url, width, height))
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .noPlaceholder()
                .into(getBinding().image);
    }

    /**
     * @param number
     * @return a random number within the limit given
     */
    private int getVariable(int number, int limit) {
        return (int) ((Math.random() * limit * 2) - limit) + number;
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxUtil.unsubscribe(subscription, subscriptionImage);
    }
}
